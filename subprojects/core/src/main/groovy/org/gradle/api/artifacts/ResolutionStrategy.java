/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.artifacts;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Defines the strategies around dependency resolution.
 * For example, forcing certain dependency versions, conflict resolutions or snapshot timeouts.
 * <p>
 * Examples:
 * <pre autoTested=''>
 * configurations.all {
 *   //fail eagerly on version conflict (includes transitive dependencies)
 *   //e.g. multiple different versions of the same dependency (group and name are equal)
 *   resolutionStrategy.failOnVersionConflict()
 *
 *   // cache dynamic versions for 10 minutes
 *   resolutionStrategy.cacheDynamicVersionsFor 10, 'minutes'
 *   // don't cache changing modules at all
 *   resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
 * }
 * </pre>
 */
public interface ResolutionStrategy {

    /**
     * In case of conflict, Gradle by default uses the latest of conflicting versions.
     * However, you change that behavior. Use this method to configure the resolution to fail eagerly on any version conflict, e.g.
     * multiple different versions of the same dependency (group and name are equal) in the same {@link Configuration}.
     * The check includes both first level and transitive dependencies.
     *
     * <pre autoTested=''>
     * configurations.all {
     *   resolutionStrategy.failOnVersionConflict()
     * }
     * </pre>
     *
     * @return this resolution strategy instance
     */
    ResolutionStrategy failOnVersionConflict();

    /**
     * <b>Experimental</b>. This part of the api is yet experimental - may change without notice.
     * <p>
     * Configures forced versions in DSL friendly fashion.
     *
     * @param forcedModules group:name:version notations
     * @return this ResolutionStrategy instance
     */
    ResolutionStrategy force(String... forcedModules);

    /**
     * <b>Experimental</b>. This part of the api is yet experimental - may change without notice.
     * <p>
     * Replaces existing forced modules with the passed ones.
     *
     * @param forcedModules forced modules to set
     * @return this ResolutionStrategy instance
     */
    ResolutionStrategy setForcedModules(Iterable<ModuleIdentifier> forcedModules);

    /**
     * <b>Experimental</b>. This part of the api is yet experimental - may change without notice.
     * <p>
     * returns currently configured forced modules
     *
     * @return forced modules
     */
    Set<ModuleIdentifier> getForcedModules();

    /**
     * A convenience method for calling {@link #cacheDynamicVersionsFor(int, java.util.concurrent.TimeUnit)} with named units.
     * Units are resolved using {@link java.util.concurrent.TimeUnit#valueOf(String)} on the upper-cased string value.
     * @param value The number of time units
     * @param units The units
     */
    void cacheDynamicVersionsFor(int value, String units);

    /**
     * Gradle keeps a cache of dynamic version => resolved version (ie 2.+ => 2.3). By default, these cached values are kept for 24 hours, after which the cached entry is expired
     * and the dynamic version is resolved again.
     * Use this method to provide a custom expiry time after which the cached value for any dynamic version will be expired.
     * @param value The number of time units
     * @param units The units
     */
    void cacheDynamicVersionsFor(int value, TimeUnit units);

    /**
     * A convenience method for calling {@link #cacheChangingModulesFor(int, java.util.concurrent.TimeUnit)} with named units.
     * Units are resolved using {@link java.util.concurrent.TimeUnit#valueOf(String)} on the upper-cased string value.
     * @param value The number of time units
     * @param units The units
     */
    void cacheChangingModulesFor(int value, String units);

    /**
     * Gradle caches the contents and artifacts of changing modules. By default, these cached values are kept for 24 hours,
     * after which the cached entry is expired and the module is resolved again.
     * Use this method to provide a custom expiry time after which the cached entries for any changing module will be expired.
     * @param value The number of time units
     * @param units The units
     */
    void cacheChangingModulesFor(int value, TimeUnit units);
}
