
package com.hunterdavis.jsongamelist.types;

import java.util.HashMap;
import java.util.Map;

public class SystemInfo {

    private String cpu;
    private String cpuSpeed;
    private String cpuRam;
    private String gpu;
    private String gpuRam;
    private String operatingSystem;
    private String operatingSystemVersion;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The cpu
     */
    public String getCpu() {
        return cpu;
    }

    /**
     * 
     * @param cpu
     *     The cpu
     */
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    /**
     * 
     * @return
     *     The cpuSpeed
     */
    public String getCpuSpeed() {
        return cpuSpeed;
    }

    /**
     * 
     * @param cpuSpeed
     *     The cpuSpeed
     */
    public void setCpuSpeed(String cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    /**
     * 
     * @return
     *     The cpuRam
     */
    public String getCpuRam() {
        return cpuRam;
    }

    /**
     * 
     * @param cpuRam
     *     The cpuRam
     */
    public void setCpuRam(String cpuRam) {
        this.cpuRam = cpuRam;
    }

    /**
     * 
     * @return
     *     The gpu
     */
    public String getGpu() {
        return gpu;
    }

    /**
     * 
     * @param gpu
     *     The gpu
     */
    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    /**
     * 
     * @return
     *     The gpuRam
     */
    public String getGpuRam() {
        return gpuRam;
    }

    /**
     * 
     * @param gpuRam
     *     The gpuRam
     */
    public void setGpuRam(String gpuRam) {
        this.gpuRam = gpuRam;
    }

    /**
     * 
     * @return
     *     The operatingSystem
     */
    public String getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * 
     * @param operatingSystem
     *     The operatingSystem
     */
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    /**
     * 
     * @return
     *     The operatingSystemVersion
     */
    public String getOperatingSystemVersion() {
        return operatingSystemVersion;
    }

    /**
     * 
     * @param operatingSystemVersion
     *     The operatingSystemVersion
     */
    public void setOperatingSystemVersion(String operatingSystemVersion) {
        this.operatingSystemVersion = operatingSystemVersion;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
