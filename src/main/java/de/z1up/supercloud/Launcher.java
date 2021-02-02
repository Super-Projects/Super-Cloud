package de.z1up.supercloud;

import de.z1up.supercloud.core.Core;
import de.z1up.supercloud.core.mongo.MongoManager;
import org.json.JSONObject;

public class Launcher ***REMOVED***

    public static void main(String[] args) ***REMOVED***

        MongoManager mongoManager = new MongoManager();

        Runtime.getRuntime().addShutdownHook(new Thread(mongoManager::disconnect));

        mongoManager.connect();

        //new Core().startUp();

    ***REMOVED***

***REMOVED***
