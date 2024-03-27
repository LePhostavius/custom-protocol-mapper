package com.example;

import org.jboss.logging.Logger;
import org.keycloak.dom.saml.v2.assertion.AttributeStatementType;
import org.keycloak.dom.saml.v2.assertion.AttributeType;
import org.keycloak.models.AuthenticatedClientSessionModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.saml.mappers.AbstractSAMLProtocolMapper;
import org.keycloak.protocol.saml.mappers.AttributeStatementHelper;
import org.keycloak.protocol.saml.mappers.SAMLAttributeStatementMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

import java.util.List;

import javax.print.attribute.standard.Destination;

public class EduPersonTargetedId extends AbstractSAMLProtocolMapper implements SAMLAttributeStatementMapper {

    private static final String PROVIDER_ID = "EduPersonTargetedId";

    private static final Logger LOGGER = Logger.getLogger(EduPersonTargetedId.class);

    private static final String CONFIG_PROPERTY = "EduPersonTargetedId";

    private static final List<ProviderConfigProperty> CONFIG_PROPERTIES;

    static {
        CONFIG_PROPERTIES = ProviderConfigurationBuilder.create()
                .property()
                .name(CONFIG_PROPERTY)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("EduPersonTargetedID")
                .helpText("Génération de l'attribut EduPersonTargetedID")
                .defaultValue("null")
                .add()
// additional properties here
                .build();

    }

    @Override
    public String getDisplayCategory() {
        return AttributeStatementHelper.ATTRIBUTE_STATEMENT_CATEGORY;
    }

    @Override
    public String getDisplayType() {
        return "EduPersonTargetedID";
    }

    @Override
    public void transformAttributeStatement(AttributeStatementType attributeStatement, ProtocolMapperModel mappingModel, KeycloakSession session, UserSessionModel userSession, AuthenticatedClientSessionModel clientSession) {

        // transform attributeStatement here
        LOGGER.infof("transformAttributeStatement");

        AttributeType eduPersonTargetedId = new AttributeType("eduPersonTargetedId");
        eduPersonTargetedId.setFriendlyName("eduPersonTargetedId");
        eduPersonTargetedId.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:basic");
        eduPersonTargetedId.setName("eduPersonTargetedId");
       
        String urlIDP = "https://10.4.1.77";
        String urlSP = getUrlSp(session);


        eduPersonTargetedId.addAttributeValue(genEduPersonTargetedID(urlIDP, urlSP));
        // see: bottom of org.keycloak.saml.processing.core.saml.v2.writers.BaseWriter.writeAttributeTypeWithoutRootTag

        
        attributeStatement.addAttribute(new AttributeStatementType.ASTChoiceType(eduPersonTargetedId));
    }

    protected String genEduPersonTargetedID(String urlIDP, String urlSP)
    {
        return urlIDP+"!"+urlSP+"!";
    }

    protected String getUrlSp(KeycloakSession KeycloakSession){
        ClientModel client = KeycloakSession.getContext().getClient();
        System.out.println("GETURL SP\n");
        System.out.println(client.toString());
        System.out.println(client.getRootUrl());
        return client != null ? client.getBaseUrl() : null;
    }

    @Override
    public String getHelpText() {
        return "urlIDP!+urlSP!+HashGUID";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return CONFIG_PROPERTIES;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}