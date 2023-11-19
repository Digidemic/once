/**
 * Once v1.0.0 - https://github.com/Digidemic/Once
 * (c) 2023 DIGIDEMIC, LLC - All Rights Reserved
 * Once developed by Adam Steinberg of DIGIDEMIC, LLC
 * License: Apache License 2.0
 *
 * ====
 *
 * Copyright 2023 DIGIDEMIC, LLC
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

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class OnceTest {

    private class ClassA {
        val localOnce = Once()
        fun byCommonInstanceId() = Once.byInstanceId(this, COMMON_INSTANCE_ID)
        fun byInstanceId(instanceId: String) = Once.byInstanceId(this, instanceId)
        fun byCommonGlobalId() = Once.byGlobalId(COMMON_GLOBAL_ID)
        fun byGlobalId(globalId: String) = Once.byGlobalId(globalId)
    }

    private class ClassB {
        val localOnce = Once.NewLocalInstance()
        fun byCommonInstanceId() = Once.byInstanceId(this, COMMON_INSTANCE_ID)
        fun byInstanceId(instanceId: String) = Once.byInstanceId(this, instanceId)
        fun byCommonGlobalId() = Once.byGlobalId(COMMON_GLOBAL_ID)
        fun byGlobalId(globalId: String) = Once.byGlobalId(globalId)
    }

    private class EmptyClass

    @Test
    fun `given Once instantiates twice with invoke and twice with NewLocalInstance, when calling isFirstUse 3 times each, then return true once for each first call`() {
        val test1 = Once()
        assertTrue(test1.isFirstUse)
        assertFalse(test1.isFirstUse)
        assertFalse(test1.isFirstUse)

        val test2 = Once.NewLocalInstance()
        assertTrue(test2.isFirstUse)
        assertFalse(test2.isFirstUse)
        assertFalse(test2.isFirstUse)

        val test3 = Once()
        assertTrue(test3.isFirstUse)
        assertFalse(test3.isFirstUse)
        assertFalse(test3.isFirstUse)

        val test4 = Once.NewLocalInstance()
        assertTrue(test4.isFirstUse)
        assertFalse(test4.isFirstUse)
        assertFalse(test4.isFirstUse)
    }

    @Test
    fun `given ClassA and ClassB, when calling localOnce multiple times each, then return true once for each first call`() {
        val classAInstance1 = ClassA()
        assertTrue(classAInstance1.localOnce.isFirstUse)
        assertFalse(classAInstance1.localOnce.isFirstUse)
        assertFalse(classAInstance1.localOnce.isFirstUse)

        val classAInstance2 = ClassA()
        assertTrue(classAInstance2.localOnce.isFirstUse)
        assertFalse(classAInstance2.localOnce.isFirstUse)
        assertFalse(classAInstance2.localOnce.isFirstUse)

        val classBInstance1 = ClassB()
        assertTrue(classBInstance1.localOnce.isFirstUse)
        assertFalse(classBInstance1.localOnce.isFirstUse)
        assertFalse(classBInstance1.localOnce.isFirstUse)

        val classBInstance2 = ClassB()
        assertTrue(classBInstance2.localOnce.isFirstUse)
        assertFalse(classBInstance2.localOnce.isFirstUse)
        assertFalse(classBInstance2.localOnce.isFirstUse)

        assertFalse(classAInstance1.localOnce.isFirstUse)
        assertFalse(classAInstance2.localOnce.isFirstUse)
        assertFalse(classBInstance1.localOnce.isFirstUse)
        assertFalse(classBInstance2.localOnce.isFirstUse)
    }

    @Test
    fun `given ClassA and ClassB, when calling byCommonInstanceId multiple times each, then return true once for each first call`() {
        val classAInstance1 = ClassA()
        assertTrue(classAInstance1.byCommonInstanceId())
        assertFalse(classAInstance1.byCommonInstanceId())
        assertFalse(classAInstance1.byCommonInstanceId())

        val classAInstance2 = ClassA()
        assertTrue(classAInstance2.byCommonInstanceId())
        assertFalse(classAInstance2.byCommonInstanceId())
        assertFalse(classAInstance2.byCommonInstanceId())

        val classBInstance1 = ClassB()
        assertTrue(classBInstance1.byCommonInstanceId())
        assertFalse(classBInstance1.byCommonInstanceId())
        assertFalse(classBInstance1.byCommonInstanceId())

        val classBInstance2 = ClassB()
        assertTrue(classBInstance2.byCommonInstanceId())
        assertFalse(classBInstance2.byCommonInstanceId())
        assertFalse(classBInstance2.byCommonInstanceId())

        assertFalse(classAInstance1.byCommonInstanceId())
        assertFalse(classAInstance2.byCommonInstanceId())
        assertFalse(classBInstance1.byCommonInstanceId())
        assertFalse(classBInstance2.byCommonInstanceId())
    }

    @Test
    fun `given EmptyClass and two ids, when calling byInstanceId in combination with the two ids multiple times, then return true once for each first call`() {
        val id1 = "id1"
        val id2 = "id2"

        val instance1 = EmptyClass()
        assertTrue(Once.byInstanceId(instance1, id1))
        assertFalse(Once.byInstanceId(instance1, id1))
        assertFalse(Once.byInstanceId(instance1, id1))

        assertTrue(Once.byInstanceId(instance1, id2))
        assertFalse(Once.byInstanceId(instance1, id2))
        assertFalse(Once.byInstanceId(instance1, id2))

        val instance2 = EmptyClass()
        assertTrue(Once.byInstanceId(instance2, id1))
        assertFalse(Once.byInstanceId(instance2, id1))
        assertFalse(Once.byInstanceId(instance2, id1))

        assertTrue(Once.byInstanceId(instance2, id2))
        assertFalse(Once.byInstanceId(instance2, id2))
        assertFalse(Once.byInstanceId(instance2, id2))

        val instance3 = EmptyClass()
        assertTrue(Once.byInstanceId(instance3, id1))
        assertFalse(Once.byInstanceId(instance3, id1))
        assertFalse(Once.byInstanceId(instance3, id1))

        val instance4 = EmptyClass()
        assertTrue(Once.byInstanceId(instance4, id1))
        assertFalse(Once.byInstanceId(instance4, id1))
        assertFalse(Once.byInstanceId(instance4, id1))

        assertTrue(Once.byInstanceId(instance3, id2))
        assertFalse(Once.byInstanceId(instance3, id2))
        assertFalse(Once.byInstanceId(instance3, id2))

        assertTrue(Once.byInstanceId(instance4, id2))
        assertFalse(Once.byInstanceId(instance4, id2))
        assertFalse(Once.byInstanceId(instance4, id2))

        assertFalse(Once.byInstanceId(instance1, id1))
        assertFalse(Once.byInstanceId(instance1, id2))
        assertFalse(Once.byInstanceId(instance2, id1))
        assertFalse(Once.byInstanceId(instance2, id2))
        assertFalse(Once.byInstanceId(instance3, id1))
        assertFalse(Once.byInstanceId(instance3, id2))
        assertFalse(Once.byInstanceId(instance4, id1))
        assertFalse(Once.byInstanceId(instance4, id2))
    }

    @Test
    fun `given ClassA, ClassB, and two ids, when calling byInstanceId in combination with the two ids multiple times, then return true once for each first call`() {
        val id1 = "id1"
        val id2 = "id2"

        val classAInstance1 = ClassA()
        assertTrue(classAInstance1.byInstanceId(id1))
        assertFalse(classAInstance1.byInstanceId(id1))
        assertFalse(classAInstance1.byInstanceId(id1))

        assertTrue(classAInstance1.byInstanceId(id2))
        assertFalse(classAInstance1.byInstanceId(id2))
        assertFalse(classAInstance1.byInstanceId(id2))

        val classAInstance2 = ClassA()
        assertTrue(classAInstance2.byInstanceId(id1))
        assertFalse(classAInstance2.byInstanceId(id1))
        assertFalse(classAInstance2.byInstanceId(id1))

        assertTrue(classAInstance2.byInstanceId(id2))
        assertFalse(classAInstance2.byInstanceId(id2))
        assertFalse(classAInstance2.byInstanceId(id2))

        val classBInstance1 = ClassB()
        assertTrue(classBInstance1.byInstanceId(id1))
        assertFalse(classBInstance1.byInstanceId(id1))
        assertFalse(classBInstance1.byInstanceId(id1))

        val classBInstance2 = ClassB()
        assertTrue(classBInstance2.byInstanceId(id1))
        assertFalse(classBInstance2.byInstanceId(id1))
        assertFalse(classBInstance2.byInstanceId(id1))

        assertTrue(classBInstance1.byInstanceId(id2))
        assertFalse(classBInstance1.byInstanceId(id2))
        assertFalse(classBInstance1.byInstanceId(id2))

        assertTrue(classBInstance2.byInstanceId(id2))
        assertFalse(classBInstance2.byInstanceId(id2))
        assertFalse(classBInstance2.byInstanceId(id2))

        assertFalse(classAInstance1.byInstanceId(id1))
        assertFalse(classAInstance1.byInstanceId(id2))
        assertFalse(classAInstance2.byInstanceId(id1))
        assertFalse(classAInstance2.byInstanceId(id2))
        assertFalse(classBInstance1.byInstanceId(id1))
        assertFalse(classBInstance1.byInstanceId(id2))
        assertFalse(classBInstance2.byInstanceId(id1))
        assertFalse(classBInstance2.byInstanceId(id2))
    }

    @Test
    fun `given ClassA and ClassB, when calling byCommonGlobalId multiple times each, then return true only the first time`() {
        val classAInstance1 = ClassA()
        assertTrue(classAInstance1.byCommonGlobalId())
        assertFalse(classAInstance1.byCommonGlobalId())
        assertFalse(classAInstance1.byCommonGlobalId())

        val classAInstance2 = ClassA()
        assertFalse(classAInstance2.byCommonGlobalId())
        assertFalse(classAInstance2.byCommonGlobalId())
        assertFalse(classAInstance2.byCommonGlobalId())

        val classBInstance1 = ClassB()
        assertFalse(classBInstance1.byCommonGlobalId())
        assertFalse(classBInstance1.byCommonGlobalId())
        assertFalse(classBInstance1.byCommonGlobalId())

        val classBInstance2 = ClassB()
        assertFalse(classBInstance2.byCommonGlobalId())
        assertFalse(classBInstance2.byCommonGlobalId())
        assertFalse(classBInstance2.byCommonGlobalId())

        assertFalse(classAInstance1.byCommonGlobalId())
        assertFalse(classAInstance2.byCommonGlobalId())
        assertFalse(classBInstance1.byCommonGlobalId())
        assertFalse(classBInstance2.byCommonGlobalId())
    }

    @Test
    fun `given ClassA, ClassB, and two ids, when calling byCommonGlobalId in combination with the two ids multiple times, then return true once for each first call`() {
        val id1 = "id1"
        val id2 = "id2"

        val classAInstance1 = ClassA()
        assertTrue(classAInstance1.byGlobalId(id1))
        assertFalse(classAInstance1.byGlobalId(id1))
        assertFalse(classAInstance1.byGlobalId(id1))

        assertTrue(classAInstance1.byGlobalId(id2))
        assertFalse(classAInstance1.byGlobalId(id2))
        assertFalse(classAInstance1.byGlobalId(id2))

        val classAInstance2 = ClassA()
        assertFalse(classAInstance2.byGlobalId(id1))
        assertFalse(classAInstance2.byGlobalId(id1))
        assertFalse(classAInstance2.byGlobalId(id1))

        assertFalse(classAInstance2.byGlobalId(id2))
        assertFalse(classAInstance2.byGlobalId(id2))
        assertFalse(classAInstance2.byGlobalId(id2))

        val classBInstance1 = ClassB()
        assertFalse(classBInstance1.byGlobalId(id1))
        assertFalse(classBInstance1.byGlobalId(id1))
        assertFalse(classBInstance1.byGlobalId(id1))

        val classBInstance2 = ClassB()
        assertFalse(classBInstance2.byGlobalId(id1))
        assertFalse(classBInstance2.byGlobalId(id1))
        assertFalse(classBInstance2.byGlobalId(id1))

        assertFalse(classBInstance1.byGlobalId(id2))
        assertFalse(classBInstance1.byGlobalId(id2))
        assertFalse(classBInstance1.byGlobalId(id2))

        assertFalse(classBInstance2.byGlobalId(id2))
        assertFalse(classBInstance2.byGlobalId(id2))
        assertFalse(classBInstance2.byGlobalId(id2))

        assertFalse(classAInstance1.byGlobalId(id1))
        assertFalse(classAInstance1.byGlobalId(id2))
        assertFalse(classAInstance2.byGlobalId(id1))
        assertFalse(classAInstance2.byGlobalId(id2))
        assertFalse(classBInstance1.byGlobalId(id1))
        assertFalse(classBInstance1.byGlobalId(id2))
        assertFalse(classBInstance2.byGlobalId(id1))
        assertFalse(classBInstance2.byGlobalId(id2))
    }

    private companion object {
        private const val COMMON_INSTANCE_ID = "123InstanceId"
        private const val COMMON_GLOBAL_ID = "123GlobalId"
    }
}