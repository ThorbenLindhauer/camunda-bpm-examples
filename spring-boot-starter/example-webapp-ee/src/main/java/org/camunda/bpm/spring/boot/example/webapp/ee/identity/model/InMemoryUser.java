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
package org.camunda.bpm.spring.boot.example.webapp.ee.identity.model;

import java.util.HashSet;
import java.util.Set;

import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;

public class InMemoryUser extends UserEntity {

  private Set<String> groupMemberships = new HashSet<>();
  private Set<String> tenantMemberships = new HashSet<>();

  public InMemoryUser(String id) {
    this.id = id;
  }

  public Set<String> getGroupMemberships() {
    return groupMemberships;
  }

  public void setGroupMemberships(Set<String> groupMemberships) {
    this.groupMemberships = groupMemberships;
  }

  public Set<String> getTenantMemberships() {
    return tenantMemberships;
  }

  public void setTenantMemberships(Set<String> tenantMemberships) {
    this.tenantMemberships = tenantMemberships;
  }

  @Override
  public String getPassword() {
    return newPassword;
  }
}
