package com.workflow_automation.workflow_service.service;

import com.workflow_automation.workflow_service.entity.Workflow;
import com.workflow_automation.workflow_service.entity.WorkflowPermission;
import com.workflow_automation.workflow_service.entity.enums.PermissionType;
import com.workflow_automation.workflow_service.repository.WorkflowPermissionRepository;
import com.workflow_automation.workflow_service.repository.WorkflowRepository;
import com.workflow_automation.workflow_service.security.AccessContext;
import com.workflow_automation.workflow_service.security.PlatformRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowAccessServiceTest {

    @Mock
    private WorkflowRepository workflowRepository;

    @Mock
    private WorkflowPermissionRepository workflowPermissionRepository;

    @InjectMocks
    private WorkflowAccessService workflowAccessService;

    @Test
    @DisplayName("canEdit() returns true for a regular user who was granted EDIT on the workflow")
    void canEdit_userWithEditGrant_returnsTrue() {
        // Arrange
        Workflow workflow = Workflow.builder()
                .id(100L)
                .userId(1L)            // owned by user 1
                .organizationId(10L)
                .build();

        AccessContext context = AccessContext.builder()
                .userId(2L)            // user 2 is NOT the owner
                .organizationId(10L)
                .role(PlatformRole.USER)
                .build();

        WorkflowPermission grant = WorkflowPermission.builder()
                .workflow(workflow)
                .userId(2L)
                .organizationId(10L)
                .permissions(EnumSet.of(PermissionType.EDIT))
                .build();

        when(workflowPermissionRepository.findByWorkflow_IdAndUserId(100L, 2L))
                .thenReturn(Optional.of(grant));

        // Act
        boolean result = workflowAccessService.canEdit(workflow, context);

        // Assert
        assertTrue(result);
        verify(workflowPermissionRepository).findByWorkflow_IdAndUserId(100L, 2L);
    }
}
