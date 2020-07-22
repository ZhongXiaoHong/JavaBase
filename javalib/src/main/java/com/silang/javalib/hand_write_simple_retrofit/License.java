package com.silang.javalib.hand_write_simple_retrofit;

import com.google.gson.annotations.SerializedName;

public class License {

    private String key;
    private String name;
    @SerializedName("spdx_id")
    private String spdxId;
    private String url;
    @SerializedName("node_id")
    private String nodeId;
    public void setKey(String key) {
         this.key = key;
     }
     public String getKey() {
         return key;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setSpdxId(String spdxId) {
         this.spdxId = spdxId;
     }
     public String getSpdxId() {
         return spdxId;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

    public void setNodeId(String nodeId) {
         this.nodeId = nodeId;
     }
     public String getNodeId() {
         return nodeId;
     }

}