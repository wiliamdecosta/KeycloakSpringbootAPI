package com.kcsb.service;

import java.util.Collections;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kcsb.config.KeycloakProvider;
import com.kcsb.requests.CreateUserRequest;

@Service
public class KeycloakAdminClientService {
    @Value("${keycloak.realm}")
    public String realm;
    
    @Value("${keycloak.resource}")
    public String client;

    private final KeycloakProvider kcProvider;


    public KeycloakAdminClientService(KeycloakProvider keycloakProvider) {
        this.kcProvider = keycloakProvider;
    }

    public Response createKeycloakUser(CreateUserRequest user) {
    	RealmResource realmResource = kcProvider.getInstance().realm(realm);
        UsersResource usersResource = realmResource.users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
        
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstname());
        kcUser.setLastName(user.getLastname());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);
        
        /*if(user.getClientRoles() != null) {
        	Map<String,List<String>> mapClientRoles = new HashMap<String,List<String>>();  
        	mapClientRoles.put(client, user.getClientRoles());
        	kcUser.setClientRoles(mapClientRoles);
        }*/
        
        Response response = usersResource.create(kcUser);
        //String userId = CreatedResponseUtil.getCreatedId(response);

        if (response.getStatus() == 201) {
        	 
        	//if email verified true
        	//UserResource userRes = realmResource.users().get(userId);
        	//userRes.sendVerifyEmail();
        	 
        	 //RoleRepresentation roleRepresentation = new RoleRepresentation();
//        	 roleRepresentation.setClientRole(true);
//        	 roleRepresentation.setName("member");
//        	 userRes.roles().clientLevel(userId).add(Arrays.asList(roleRepresentation));
        	
        	//If you want to save the user to your other database, do it here, for example:
//            User localUser = new User();
//            localUser.setFirstName(kcUser.getFirstName());
//            localUser.setLastName(kcUser.getLastName());
//            localUser.setEmail(user.getEmail());
//            localUser.setCreatedDate(Timestamp.from(Instant.now()));
//            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//            usersResource.get(userId).sendVerifyEmail();
//            userRepository.save(localUser);
        }

        return response;

    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }


}

