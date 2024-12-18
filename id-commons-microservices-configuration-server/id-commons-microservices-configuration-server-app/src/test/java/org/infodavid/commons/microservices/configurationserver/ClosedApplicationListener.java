package org.infodavid.commons.microservices.configurationserver;

import java.io.IOException;

import org.infodavid.commons.test.docker.DockerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;

/**
 * The Class ClosedApplicationListener.
 */
@Component
public class ClosedApplicationListener implements ApplicationListener<ContextClosedEvent>, SmartLifecycle {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClosedApplicationListener.class);

    /** The already processed flag. */
    private boolean alreadyProcessed = false;

    /*
     * (non-Javadoc)
     * @see org.springframework.context.SmartLifecycle#getPhase()
     */
    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.context.Lifecycle#isRunning()
     */
    @Override
    public boolean isRunning() {
        return !alreadyProcessed;
    }

    /*
     * (non-javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework. context.ApplicationEvent)
     */
    @Override
    public synchronized void onApplicationEvent(final @NonNull ContextClosedEvent event) {
        if (alreadyProcessed) {
            LOGGER.trace("Already processed, ignored");

            return;
        }

        LOGGER.debug("Handling application closed ({})", Thread.currentThread().getName());

        try (DockerClient client = DockerContainer.getDockerClient()) {
            DockerContainer.deleteContainer(client, DockerContainer.containerExists(client, null, "postgres"));
        } catch (final IOException e) {
            LOGGER.warn("Failed to delete the Docker container", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.context.Lifecycle#start()
     */
    @Override
    public void start() {
        LOGGER.debug("Starting ({})", Thread.currentThread().getName());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.context.Lifecycle#stop()
     */
    @Override
    public void stop() {
        LOGGER.debug("Stopping ({})", Thread.currentThread().getName());
    }
}
