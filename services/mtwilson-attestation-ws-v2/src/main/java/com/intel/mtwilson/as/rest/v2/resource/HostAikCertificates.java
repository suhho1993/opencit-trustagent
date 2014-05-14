/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.as.rest.v2.resource;

import com.intel.mtwilson.as.rest.v2.model.HostAikCertificate;
import com.intel.mtwilson.as.rest.v2.model.HostAikCertificateCollection;
import com.intel.mtwilson.as.rest.v2.model.HostAikCertificateFilterCriteria;
import com.intel.mtwilson.as.rest.v2.model.HostAikCertificateLocator;
import com.intel.mtwilson.as.rest.v2.repository.HostAikCertificateRepository;
import com.intel.mtwilson.jaxrs2.NoLinks;
import com.intel.mtwilson.jaxrs2.server.resource.AbstractCertificateJsonapiResource;
import com.intel.mtwilson.launcher.ws.ext.V2;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ssbangal
 */
@V2
@Path("/hosts/{host_id}/aik-certificates")
public class HostAikCertificates extends AbstractCertificateJsonapiResource<HostAikCertificate, HostAikCertificateCollection, HostAikCertificateFilterCriteria, NoLinks<HostAikCertificate>, HostAikCertificateLocator> {

    private Logger log = LoggerFactory.getLogger(getClass().getName());
    private HostAikCertificateRepository repository;

    public HostAikCertificates(HostAikCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    protected HostAikCertificateCollection createEmptyCollection() {
        return new HostAikCertificateCollection();
    }

    @Override
    protected HostAikCertificateRepository getRepository() {
        return repository;
    }    
    
}
