/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.spring.boot.example.web;

import java.util.logging.Logger;

import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingFeature.Verbosity;
import org.springframework.stereotype.Component;

/**
 * @author Thorben Lindhauer
 *
 */
@Component
public class JerseyConfiguration extends CamundaJerseyResourceConfig {

  private static final Logger log = Logger.getLogger(JerseyConfiguration.class.getName());

  @Override
  public void afterPropertiesSet() throws Exception {
    super.afterPropertiesSet();
    register(new LoggingFeature(log));
//    register(new LoggingFeature(log, null, Verbosity.PAYLOAD_ANY, 128));
  }
}
