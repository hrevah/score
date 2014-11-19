/*
 * Licensed to Hewlett-Packard Development Company, L.P. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/
package org.eclipse.score.samples.openstack;

import org.eclipse.score.api.TriggeringProperties;
import org.eclipse.score.samples.openstack.actions.ExecutionPlanBuilder;
import org.eclipse.score.samples.openstack.actions.MatchType;
import org.eclipse.score.samples.openstack.actions.NavigationMatcher;
import org.eclipse.score.samples.openstack.actions.SimpleSendEmail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.eclipse.score.samples.openstack.OpenstackCommons.SEND_EMAIL_CLASS;
import static org.eclipse.score.samples.openstack.OpenstackCommons.SEND_EMAIL_METHOD;
import static org.eclipse.score.samples.openstack.OpenstackCommons.createFailureStep;
import static org.eclipse.score.samples.openstack.OpenstackCommons.createSuccessStep;

/**
* Created by peerme on 04/09/2014.
*/
public class SendMailForDemo {

    public TriggeringProperties sendMail(String toMailAddress) {
        ExecutionPlanBuilder builder = new ExecutionPlanBuilder("health check");

        Long successId = 6L;
        Long sendEmailId = 0L;
        Long failureId = 8L;


        createSendEmailStep(builder, sendEmailId, failureId);

        createSuccessStep(builder, successId);

        createFailureStep(builder, failureId);

        Map<String,String> executionContext = createSendExecutionContext(toMailAddress);


        return builder.createTriggeringProperties().setContext(executionContext);
    }

    private Map<String,String> createSendExecutionContext(String toMail) {

        Map<String,String> executionContext = new HashMap<>();
        executionContext.put("host", "smtp-americas.hp.com");
        executionContext.put("port", "25");
        executionContext.put("subject", "Demo mail");
        executionContext.put("body", "This is great!!!!");
        executionContext.put("from", "orit.stone@hp.com");
        executionContext.put("to", toMail);
        return executionContext;
    }

    private void createSendEmailStep(ExecutionPlanBuilder builder, Long sendEmailId, Long failureId) {
        List<NavigationMatcher<Serializable>> navigationMatchers;//send email step
        navigationMatchers = new ArrayList<>();
        navigationMatchers.add(new NavigationMatcher<Serializable>(MatchType.EQUAL, SimpleSendEmail.RETURN_CODE, SimpleSendEmail.SUCCESS, failureId));
        navigationMatchers.add(new NavigationMatcher<Serializable>(MatchType.DEFAULT, failureId));
        builder.addOOActionStep(sendEmailId, SEND_EMAIL_CLASS, SEND_EMAIL_METHOD, null, navigationMatchers);
    }
}