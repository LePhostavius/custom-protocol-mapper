# custom-protocol-mapper
Keycloak test custom protocol mapper

Fonctionnement basique et objectifs : 
L'utilisateur veut se connecter à son application (Service Provider dit SP), 
l'authentification est déléguée à un fournisseur d'identité Keycloak (Identity Provider IdP). 
De ce fait les étapes pour la connexion sont : 

1) Arrivée du client sur le SP
2) Redirection du SP vers l'IDP pour la connexion
3) Validation de la connexion par l'IdP, génération d'une assertion SAML
4) Redirection de l'IdP vers le SP
5) Le client a accés à son application

Je cherche à générer un attribut EduPersonTargetedID qui prend en paramètres
l'url de l'idp + l'url du sp + HASH(GUID)

J'ai pour l'instant les 2 url. Il me manque le GUID.
Pour accéder au GUID de l'utilisateur qui tente de se connecter, je dois récupérer l'username (email) qu'il rentre
dans le formulaire de connexion sur Keycloak afin d'aller chercher son attribut. 
Exemple User = session.getUserByUsername(username)
String GUID = user.getAttribute(GUID)

Pour récupérer l'email de l'utilisateur qui se connecte j'ai besoin d'aller chercher dans une assertion SAML ou un autre méthode
l'attribut en question.

Classe EduPersonTargetedID
Toutes les méthodes avant :  public void transformAttributeStatement(AttributeStatementType attributeStatement, ProtocolMapperModel mappingModel, KeycloakSession session, UserSessionModel userSession, AuthenticatedClientSessionModel clientSession) sont des méthodes override pour initialiser le mapper sur Keycloak elles ne doivent pas être changées. 

Les autres méthodes fonctionnent bien. 
