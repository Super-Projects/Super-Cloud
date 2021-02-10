package de.z1up.supercloud.core.input;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import de.z1up.supercloud.core.Utils;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;

public class CloudInfo {

    private double version;
    private String name;
    private BuildStatus build;
    private String author;
    private String[] contributors;
    private String desc;
    private String troubleShooting;
    private String repo;

    public static CloudInfo load() {

        InputStream in = CloudInfo.class.getClassLoader().getResourceAsStream("cloud.json");

        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(in));
        CloudInfo info = gson.fromJson(reader, CloudInfo.class);
        return info;
    }

    public CloudInfo(double version, String name, BuildStatus build, String author, String[] contributors, String desc, String troubleShooting, String repo) {
        this.version = version;
        this.name = name;
        this.build = build;
        this.author = author;
        this.contributors = contributors;
        this.desc = desc;
        this.troubleShooting = troubleShooting;
        this.repo = repo;
    }

    public double getVersion() {
        return version;
    }

    public String getAbsoluteVersion() {
        return this.name + " " + (build == BuildStatus.BUILD ? "" : this.getBuildShorted()) + this.getVersion();
    }

    public String getName() {
        return name;
    }

    public BuildStatus getBuild() {
        return build;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getContributors() {
        return contributors;
    }

    public String getDesc() {
        return desc;
    }

    public String getTroubleShooting() {
        return troubleShooting;
    }

    public String getRepo() {
        return repo;
    }

    public String getContributorsString() {
        String s = "";
        for (String contributor : contributors) {
            s = contributor + ", ";
        }
        return s;
    }

    public String getBuildShorted() {

        final String buildName
                = this.build.toString();

        final char[] c
                = new char[]{buildName.charAt(0)};

        final String shorted
                = String.copyValueOf(c).toLowerCase();

        return shorted;
    }

    public boolean checkBuild() {

        if(this.build != BuildStatus.BUILD)
            return true;
        return false;
    }

    private enum BuildStatus {
        BETA, SNAPSHOT, BUILD, TESTING, DEVELOPMENT
    }

}
