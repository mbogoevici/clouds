package org.jboss.seam.jclouds.test.blobstore;

import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.jboss.seam.jclouds.blobstore.JCloudsBlobStore;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jclouds.blobstore.AsyncBlobStore;
import org.jclouds.blobstore.BlobMap;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.InputStreamMap;

/**
 * Our "real" {@link BlobStore}, backed by S3
 *
 * @author pmuir
 */
public class S3BlobStoreProducer {

    public static final String CONTAINER = "cdi-jclouds-test";

    /**
     * Configure JClouds to create a blob store on S3. If a {@link BlobMap} or
     * {@link InputStreamMap} is injected, then it will automatically use the
     * container "cdi-jclouds-test". If {@link BlobStore} or
     * {@link AsyncBlobStore} is injected, any container can be accessed.
     * {@link BlobMap}, and a {@link InputStreamMap}.
     *
     * @param awsProperties the properties containing the properties
     *                      jclouds.identity and jclouds.credential needed to access AWS
     * @return the {@link Properties} providing AWS credentials
     */
    @Produces
    @JCloudsBlobStore(provider = "s3", container = CONTAINER)
    @TestContainer
    @RequestScoped
    public Properties getS3TestContainerProperties(@Resource("aws.properties") Properties awsProperties) {
        return awsProperties;
    }

}
