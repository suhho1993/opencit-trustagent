/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.as.rest.v2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.intel.mtwilson.jersey.DocumentCollection;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author ssbangal
 */
@JacksonXmlRootElement(localName="tls_policy_collection")
public class HostTlsPolicyCollection extends DocumentCollection<HostTlsPolicy> {
    private final ArrayList<HostTlsPolicy> tlsPolicies = new ArrayList<HostTlsPolicy>();
    
    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS) // jackson 1.9
    @JsonInclude(JsonInclude.Include.ALWAYS)                // jackson 2.0
    @JacksonXmlElementWrapper(localName="tls_policies")
    @JacksonXmlProperty(localName="tls_policy")    
    public List<HostTlsPolicy> getTlsPolicies() { return tlsPolicies; }

    @Override
    public List<HostTlsPolicy> getDocuments() {
        return getTlsPolicies();
    }
    
}