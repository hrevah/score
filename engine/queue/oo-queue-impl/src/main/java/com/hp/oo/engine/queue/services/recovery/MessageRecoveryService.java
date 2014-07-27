package com.hp.oo.engine.queue.services.recovery;

import com.hp.oo.engine.queue.entities.ExecStatus;
import com.hp.oo.engine.queue.entities.ExecutionMessage;

import java.util.List;

/**
 * User: varelasa
 * Date: 22/07/14
 * Time: 13:20
 */
public interface MessageRecoveryService {

    boolean recoverMessagesBulk(String workerName, int defaultPoolSize);

    void logMessageRecovery(List<ExecutionMessage> messages);

    void enqueueMessages(List<ExecutionMessage> messages, ExecStatus messageStatus);
}
