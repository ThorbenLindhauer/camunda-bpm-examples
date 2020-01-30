/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.spring.boot.example.webapp.ee.identity;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.identity.IdentityOperationResult;
import org.camunda.bpm.engine.impl.identity.WritableIdentityProvider;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryGroup;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryTenant;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryUser;

public class InMemoryWritableIdentityProvider extends InMemoryReadableIdentityProvider implements WritableIdentityProvider {

  @Override
  public User createNewUser(String userId) {
    return new InMemoryUser(userId);
  }

  @Override
  public IdentityOperationResult saveUser(User user) {
    User existingUser = findUserById(user.getId());

    if (existingUser == null) {
      USERS.add((InMemoryUser) user);
    }

    return new IdentityOperationResult(null,
        existingUser != null ? IdentityOperationResult.OPERATION_UPDATE : IdentityOperationResult.OPERATION_CREATE);
  }

  @Override
  public IdentityOperationResult deleteUser(String userId) {

    User existingUser = findUserById(userId);
    if (existingUser != null) {
      USERS.remove(existingUser);
      return new IdentityOperationResult(null, IdentityOperationResult.OPERATION_DELETE);
    }
    else {
      throw new BadUserRequestException("User does not exist");
    }

  }

  @Override
  public IdentityOperationResult unlockUser(String userId) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public Group createNewGroup(String groupId) {
    return new InMemoryGroup(groupId);
  }

  @Override
  public IdentityOperationResult saveGroup(Group group) {
    Group existingGroup = findGroupById(group.getId());

    if (existingGroup == null) {
      GROUPS.add((InMemoryGroup) group);
    }

    return new IdentityOperationResult(null,
        existingGroup != null ? IdentityOperationResult.OPERATION_UPDATE : IdentityOperationResult.OPERATION_CREATE);
  }

  @Override
  public IdentityOperationResult deleteGroup(String groupId) {

    Group existingGroup = findGroupById(groupId);
    if (existingGroup != null) {
      GROUPS.remove(existingGroup);
      return new IdentityOperationResult(null, IdentityOperationResult.OPERATION_DELETE);
    }
    else {
      throw new BadUserRequestException("Group does not exist");
    }
  }

  @Override
  public Tenant createNewTenant(String tenantId) {
    return new InMemoryTenant(tenantId);
  }

  @Override
  public IdentityOperationResult saveTenant(Tenant tenant) {

    Tenant existingTenant = findTenantById(tenant.getId());

    if (existingTenant == null) {
      TENANTS.add((InMemoryTenant) tenant);
    }

    return new IdentityOperationResult(null,
        existingTenant != null ? IdentityOperationResult.OPERATION_UPDATE : IdentityOperationResult.OPERATION_CREATE);
  }

  @Override
  public IdentityOperationResult deleteTenant(String tenantId) {

    Tenant existingTenant = findTenantById(tenantId);
    if (existingTenant != null) {
      TENANTS.remove(existingTenant);
      return new IdentityOperationResult(null, IdentityOperationResult.OPERATION_DELETE);
    }
    else {
      throw new BadUserRequestException("Tenant does not exist");
    }
  }

  @Override
  public IdentityOperationResult createMembership(String userId, String groupId) {
    InMemoryUser user = (InMemoryUser) findUserById(userId);
    InMemoryGroup group = (InMemoryGroup) findGroupById(groupId);

    group.getMembers().add(userId);
    user.getGroupMemberships().add(groupId);

    return new IdentityOperationResult(null, IdentityOperationResult.OPERATION_CREATE);
  }

  @Override
  public IdentityOperationResult deleteMembership(String userId, String groupId) {
    InMemoryUser user = (InMemoryUser) findUserById(userId);
    InMemoryGroup group = (InMemoryGroup) findGroupById(groupId);

    boolean removed = group.getMembers().remove(userId);
    user.getGroupMemberships().remove(groupId);

    return new IdentityOperationResult(null, removed ? IdentityOperationResult.OPERATION_DELETE : IdentityOperationResult.OPERATION_NONE);
  }

  @Override
  public IdentityOperationResult createTenantUserMembership(String tenantId, String userId) {
    InMemoryUser user = (InMemoryUser) findUserById(userId);
    InMemoryTenant tenant = (InMemoryTenant) findTenantById(tenantId);

    tenant.getUserMembers().add(userId);
    user.getTenantMemberships().add(tenantId);

    return new IdentityOperationResult(null, IdentityOperationResult.OPERATION_CREATE);
  }

  @Override
  public IdentityOperationResult createTenantGroupMembership(String tenantId, String groupId) {
    InMemoryGroup group = (InMemoryGroup) findGroupById(groupId);
    InMemoryTenant tenant = (InMemoryTenant) findTenantById(tenantId);

    tenant.getGroupMembers().add(groupId);
    group.getTenantMemberships().add(tenantId);

    return new IdentityOperationResult(null, IdentityOperationResult.OPERATION_CREATE);
  }

  @Override
  public IdentityOperationResult deleteTenantUserMembership(String tenantId, String userId) {
    InMemoryUser user = (InMemoryUser) findUserById(userId);
    InMemoryTenant tenant = (InMemoryTenant) findTenantById(tenantId);

    boolean removed = tenant.getUserMembers().remove(userId);
    user.getTenantMemberships().remove(tenantId);

    return new IdentityOperationResult(null, removed ? IdentityOperationResult.OPERATION_DELETE : IdentityOperationResult.OPERATION_NONE);
  }

  @Override
  public IdentityOperationResult deleteTenantGroupMembership(String tenantId, String groupId) {
    InMemoryGroup group = (InMemoryGroup) findGroupById(groupId);
    InMemoryTenant tenant = (InMemoryTenant) findTenantById(tenantId);

    boolean removed = tenant.getGroupMembers().remove(groupId);
    group.getTenantMemberships().remove(tenantId);

    return new IdentityOperationResult(null, removed ? IdentityOperationResult.OPERATION_DELETE : IdentityOperationResult.OPERATION_NONE);
  }

}
