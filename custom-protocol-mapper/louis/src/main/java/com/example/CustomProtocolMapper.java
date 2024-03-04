package com.example;
import java.util.ArrayList;
import java.util.List;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;



public class CustomProtocolMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper,
OIDCIDTokenMapper, UserInfoTokenMapper{

    // Déclaration des variables en haut 

    //Nom du mapper à afficher dans la liste des mappers
    public static final String PROVIDER_ID = "custom-protocol-mapper";

    //Variables de test
    static final String LOWER_BOUND = "lower_bound";
    static final String UPPER_BOUND = "upper_bound";
    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    //Afficher les champs/variables sur la page keycloak du mapper
    static {
        configProperties.add(new ProviderConfigProperty(LOWER_BOUND, "Lower Bound","Seuil minimal",ProviderConfigProperty.STRING_TYPE,1));
        configProperties.add(new ProviderConfigProperty(UPPER_BOUND, "Upper Bound","Seuil minimal",ProviderConfigProperty.STRING_TYPE,100));
        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
		OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, CustomProtocolMapper.class);
    }

    //Getter() Setter()

    @Override
    public String getDisplayCategory() {
        return "LOUIS";
    }

    @Override
    public String getDisplayType() {
        return "LOUIS";
    }

    @Override
    public String getHelpText() {
        return "LOUIS";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    //Logique du mapper 
    @Override
	protected void setClaim(IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession, KeycloakSession keycloakSession, ClientSessionContext clientSessionCtx) {
		int lower = Integer.parseInt(mappingModel.getConfig().get(LOWER_BOUND));
		int upper = Integer.parseInt(mappingModel.getConfig().get(UPPER_BOUND));

		int luckyNumber = (int) (Math.random() * (upper - lower)) + lower;

		OIDCAttributeMapperHelper.mapClaim(token, mappingModel, luckyNumber);
	}
}
