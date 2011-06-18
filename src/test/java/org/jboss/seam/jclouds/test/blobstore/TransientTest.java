package org.jboss.seam.jclouds.test.blobstore;

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.jclouds.blobstore.JCloudsBlobStore;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jclouds.blobstore.TransientAsyncBlobStore;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@Ignore
public class TransientTest {

    /**
     * Enable the transient blob store alternative
     */
    public static final Asset BEANS_XML = new ByteArrayAsset(("<beans><alternatives><class>" + TransientBlobStoreProducer.class.getName() + "</class></alternatives></beans>").getBytes());

    // Configure the object name
    private static final String KEY = "testblob";

    // Configure Arquillian
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addPackage(TransientTest.class.getPackage())
                .addPackage(JCloudsBlobStore.class.getPackage())
                .addAsManifestResource(BEANS_XML, "beans.xml");
    }

    @Inject
    private Consumer consumer;

    @Test
    public void test() throws Exception {
        consumer.putInBlobStore(KEY, "Pete!");
        assertTrue(consumer.getBlobStore() instanceof TransientAsyncBlobStore);
        String result = consumer.getFromBlobStore(KEY);
        assertEquals("Pete!", result);
    }


}
