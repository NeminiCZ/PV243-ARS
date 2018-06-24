package cz.muni.fi.pv243.ars.batching;

import javax.batch.api.listener.AbstractJobListener;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by mminatova on 6/24/18.
 */
@Named("jobListener")
public class JobListener extends AbstractJobListener {

    @Inject
    private JobContext jobContext;

    @Inject
    private Logger log;

    @Override
    public void beforeJob() {
        log.info("Started batch job with name " + jobContext.getJobName() + " and id: " + jobContext.getExecutionId());
    }

    @Override
    public void afterJob() {
        log.info("Ended batch job with name " + jobContext.getJobName() +
                " and id: "+ jobContext.getExecutionId() + " with status " + jobContext.getBatchStatus().toString());
    }
}
