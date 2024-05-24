/**
 * Once v1.1.0 - https://github.com/Digidemic/once
 * (c) 2024 DIGIDEMIC, LLC - All Rights Reserved
 * Once developed by Adam Steinberg of DIGIDEMIC, LLC
 * License: Apache License 2.0
 *
 * ====
 *
 * Copyright 2024 DIGIDEMIC, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digidemic.once

/**
 * Pass custom IDs or create variables that return 'true' only the first time used... Once!
 *
 * 3 different usage examples:
 *  Once().isFirstUse
 *  Once.byInstanceId(this, "customInstanceId")
 *  Once.byGlobalId("customGlobalId")
 */
object Once {

    /** Collection of all IDs used. Includes combined instance hash with IDs as well as global IDs. */
    @JvmStatic
    private val idsUsed = hashSetOf<String>()

    /**
     * Instantiates a new NewLocalInstance.
     * Calling [isFirstUse] property for the first time only will return true for that created instance.
     */
    class NewLocalInstance {
        var isFirstUse = false
            get() {
                if(field) {
                    return false
                }
                isFirstUse = true
                return field
            }
            private set
    }

    /**
     * Shorthand to instantiate [NewLocalInstance] by using Once() instead of Once.NewLocalInstance().
     * See [NewLocalInstance] for usage.
     */
    operator fun invoke() = NewLocalInstance()

    /**
     * Takes the passed [instance] of class and [id] returning true only the first time.
     * Same [id] but different or newly created [instance] will return true once for that instance as well.
     *
     * See [byGlobalId] for the ability to use the same ID everywhere in your project to persist
     * the results throughout the life of your application rather than just for the instance.
     */
    fun<T: Any> byInstanceId(instance: T, id: String): Boolean {
        val instanceHash = instance.hashCode()
        val instanceId = "$instanceHash-$id"
        return isFirstUseWithId(instanceId)
    }

    /**
     * The first time a specific [id] is passed to [byGlobalId] anywhere in the application for the life of the application, true will be
     * returned. Everytime after, false will be returned with that same ID when passed into [byGlobalId] for the remainder of
     * the life of the application.
     *
     * See [byInstanceId] for the ability to use the same ID tied to an instance rather than
     * everywhere in your project to persist the results throughout the life of your application.
     */
    fun byGlobalId(id: String): Boolean = isFirstUseWithId(id)

    private fun isFirstUseWithId(id: String): Boolean {
        synchronized(this) {
            if (idsUsed.contains(id)) {
                return false
            }
            idsUsed.add(id)
            return true
        }
    }
}