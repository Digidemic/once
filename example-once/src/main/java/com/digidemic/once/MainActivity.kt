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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.digidemic.once.MainActivity.Companion.TAB_1
import com.digidemic.once.MainActivity.Companion.TAB_2
import com.digidemic.once.ui.theme.GrayText
import com.digidemic.once.ui.theme.OnceTheme
import com.digidemic.once.ui.theme.OrangeText
import com.digidemic.once.ui.theme.PurpleText
import com.digidemic.once.ui.theme.WhiteText
import com.digidemic.once.ui.theme.YellowText
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val mainActivityLocalOnce = Once()
    var outerClass = OuterClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content(this@MainActivity)
        }
    }

    fun testMainActivityLocalOnce() =
        mainActivityLocalOnce.isFirstUse

    companion object {
        const val TAB_1 = "    "
        const val TAB_2 = TAB_1 + TAB_1
    }
}

class OuterClass {

    private val outerClassLocalOnce = Once()
    fun testOuterClassLocalOnce() =
        outerClassLocalOnce.isFirstUse
}

data class InstanceId (
    val id: String,
    val hash: Int,
    var count: Int
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Content(mainActivity: MainActivity) {
    var outerClassInstanceHash by remember { mutableIntStateOf(mainActivity.outerClass.hashCode()) }

    var mainLocalOnceLatestResult: Boolean? by remember { mutableStateOf(null) }
    var mainLocalOnceCount by remember { mutableIntStateOf(0) }

    var mainInstanceIdLatestResult: Boolean? by remember { mutableStateOf(null) }
    var mainInstanceIdCount by remember { mutableIntStateOf(0) }
    var mainInstanceIdText by remember { mutableStateOf(TextFieldValue("I_id")) }

    var mainGlobalIdLatestResult: Boolean? by remember { mutableStateOf(null) }
    var mainGlobalIdCount by remember { mutableIntStateOf(0) }
    var mainGlobalIdText by remember { mutableStateOf(TextFieldValue("G_id")) }

    var outerLocalOnceLatestResult: Boolean? by remember { mutableStateOf(null) }
    var outerLocalOnceCount by remember { mutableIntStateOf(0) }

    var outerInstanceIdLatestResult: Boolean? by remember { mutableStateOf(null) }
    var outerInstanceIdCount by remember { mutableIntStateOf(0) }
    var outerInstanceIdText by remember { mutableStateOf(TextFieldValue("I_id")) }

    var outerGlobalIdLatestResult: Boolean? by remember { mutableStateOf(null) }
    var outerGlobalIdCount by remember { mutableIntStateOf(0) }
    var outerGlobalIdText by remember { mutableStateOf(TextFieldValue("G_id")) }

    val globalIdMap = remember { mutableStateMapOf<String, Int>() }
    val mainInstanceIdList = remember { mutableStateListOf<InstanceId>() }
    val outerInstanceIdList = remember { mutableStateListOf<InstanceId>() }

    OnceTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    SectionTitle("Once Demo App")
                    SectionSubtitle("Scroll and tap a function to test")
                }
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    FlowRow {
                        Text(text = "// MainActivity hash: ${mainActivity.hashCode()}", color = GrayText)
                    }
                    FlowRow {
                        Text(text = "class ", color = OrangeText)
                        Text(text = "MainActivity ", color = WhiteText)
                        Text(text = "{", color = WhiteText)
                    }
                    FlowRow {
                        Text(text = TAB_1)
                        Text(text = "val ", color = OrangeText)
                        Text(text = "mainActivityLocalOnce ", color = PurpleText)
                        Text(text = "= Once()", color = WhiteText)
                    }
                    FlowRow {
                        Text(text = TAB_1)
                        Text(text = "var ", color = OrangeText)
                        Text(text = "outerClass ", color = PurpleText)
                        Text(text = "= OuterClass()", color = WhiteText)
                    }
                    SpacingBetweenFunc()
                    FlowRow {
                        Text(text = TAB_1)
                        Text(text = "// Current OuterClass hash: $outerClassInstanceHash", color = GrayText)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_1)
                        Text(text = "fun ", color = OrangeText)
                        Button(
                            onClick = {
                                mainActivity.outerClass = OuterClass()
                                outerClassInstanceHash = mainActivity.outerClass.hashCode()
                                outerLocalOnceCount = 0
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YellowText)
                        ) {
                            Text(text = "reInstantiateOuterClass")
                        }
                        Text(text = "() {", color = WhiteText)
                    }
                    FlowRow(horizontalArrangement = Arrangement.End) {
                        Text(text = TAB_2)
                        Text(text = "outerClass ", color = PurpleText)
                        Text(text = "= OuterClass() ", color = WhiteText)
                    }
                    FlowRow {
                        Text(text = TAB_1)
                        Text(text = "}", color = WhiteText)
                    }
                    SpacingBetweenFunc()
                    FunLastReturnedText(mainLocalOnceCount, mainLocalOnceLatestResult, mainLocalOnceCount)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_1)
                        Text(text = "fun ", color = OrangeText)
                        Button(
                            onClick = {
                                mainLocalOnceLatestResult = mainActivity.testMainActivityLocalOnce()
                                mainLocalOnceCount += 1
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YellowText)
                        ) {
                            Text(text = "testMainLocalOnce")
                        }
                        Text(text = "() =", color = WhiteText)
                    }
                    FlowRow {
                        Text(text = TAB_2)
                        Text(text = "mainActivityLocalOnce", color = PurpleText)
                        Text(text = ".", color = WhiteText)
                        Text(text = "isFirstUse", color = PurpleText)
                    }
                    SpacingBetweenFunc()
                    FunLastReturnedText(mainInstanceIdCount, mainInstanceIdLatestResult)
                    IdsUsedColumn(list = mainInstanceIdList)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_1)
                        Text(text = "fun ", color = OrangeText)
                        Button(
                            onClick = {
                                mainInstanceIdLatestResult = Once.byInstanceId(mainActivity, mainInstanceIdText.text)
                                mainInstanceIdCount += 1

                                val instanceId = mainInstanceIdList.firstOrNull {
                                    it.hash == mainActivity.hashCode() && it.id == mainInstanceIdText.text
                                }
                                if(instanceId != null) {
                                    instanceId.count += 1
                                } else {
                                    mainInstanceIdList.add(
                                        InstanceId(
                                            hash = mainActivity.hashCode(),
                                            id = mainInstanceIdText.text,
                                            count = 1,
                                        )
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YellowText)
                        ) {
                            Text(text = "testMainInstanceId")
                        }
                        Text(text = "() =", color = WhiteText)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_2)
                        Text(text = "Once.byInstanceId(", color = WhiteText)
                        Text(text = "this, ", color = OrangeText)
                        IdInputBox(mainInstanceIdText) {
                            mainInstanceIdText = it
                        }
                        Text(text = ")", color = WhiteText)
                    }
                    SpacingBetweenFunc()
                    FunLastReturnedText(mainGlobalIdCount, mainGlobalIdLatestResult)
                    IdsUsedColumn(map = globalIdMap.toMap())
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_1)
                        Text(text = "fun ", color = OrangeText)
                        Button(
                            onClick = {
                                mainGlobalIdLatestResult = Once.byGlobalId(mainGlobalIdText.text)
                                mainGlobalIdCount += 1
                                val count = globalIdMap.getOrDefault(mainGlobalIdText.text, 0)
                                globalIdMap[mainGlobalIdText.text] = count + 1
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YellowText)
                        ) {
                            Text(text = "testMainGlobalId")
                        }
                        Text(text = "() =", color = WhiteText)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_2)
                        Text(text = "Once.byGlobalId(", color = WhiteText)
                        IdInputBox(mainGlobalIdText) {
                            mainGlobalIdText = it
                        }
                        Text(text = ")", color = WhiteText)
                    }
                    Text(text = "}", color = WhiteText)
                    Text(text = "")
                    FlowRow {
                        Text(text = "class ", color = OrangeText)
                        Text(text = "OuterClass {", color = WhiteText)
                    }
                    FlowRow {
                        Text(text = TAB_1)
                        Text(text = "// Called from MainActivity.outerClass ", color = GrayText)
                    }
                    Text(text = "")
                    FlowRow {
                        Text(text = TAB_1)
                        Text(text = "val ", color = OrangeText)
                        Text(text = "outerClassLocalOnce ", color = PurpleText)
                        Text(text = "= Once()", color = WhiteText)
                    }
                    SpacingBetweenFunc()
                    FunLastReturnedText(outerLocalOnceCount, outerLocalOnceLatestResult, outerLocalOnceCount)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_1)
                        Text(text = "fun ", color = OrangeText)
                        Button(
                            onClick = {
                                outerLocalOnceLatestResult = mainActivity.outerClass.testOuterClassLocalOnce()
                                outerLocalOnceCount += 1
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YellowText)
                        ) {
                            Text(text = "testOuterLocalOnce")
                        }
                        Text(text = "() =", color = WhiteText)
                    }
                    FlowRow {
                        Text(text = TAB_2)
                        Text(text = "outerClassLocalOnce", color = PurpleText)
                        Text(text = ".", color = WhiteText)
                        Text(text = "isFirstUse", color = PurpleText)
                    }
                    SpacingBetweenFunc()
                    FunLastReturnedText(outerInstanceIdCount, outerInstanceIdLatestResult)
                    IdsUsedColumn(list = outerInstanceIdList)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_1)
                        Text(text = "fun ", color = OrangeText)
                        Button(
                            onClick = {
                                outerInstanceIdLatestResult =
                                    Once.byInstanceId(mainActivity.outerClass, outerInstanceIdText.text)
                                outerInstanceIdCount += 1

                                val instanceId = outerInstanceIdList.firstOrNull {
                                    it.hash == mainActivity.outerClass.hashCode() && it.id == outerInstanceIdText.text
                                }
                                if(instanceId != null) {
                                    instanceId.count += 1
                                } else {
                                    outerInstanceIdList.add(
                                        InstanceId(
                                            hash = mainActivity.outerClass.hashCode(),
                                            id = outerInstanceIdText.text,
                                            count = 1,
                                        )
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YellowText)
                        ) {
                            Text(text = "testOuterInstanceId")
                        }
                        Text(text = "() =", color = WhiteText)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_2)
                        Text(text = "Once.byInstanceId(", color = WhiteText)
                        Text(text = "this, ", color = OrangeText)
                        IdInputBox(outerInstanceIdText) {
                            outerInstanceIdText = it
                        }
                        Text(text = ")", color = WhiteText)
                    }
                    SpacingBetweenFunc()
                    FunLastReturnedText(outerGlobalIdCount, outerGlobalIdLatestResult)
                    IdsUsedColumn(map = globalIdMap)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_1)
                        Text(text = "fun ", color = OrangeText)
                        Button(
                            onClick = {
                                outerGlobalIdLatestResult = Once.byGlobalId(outerGlobalIdText.text)
                                outerGlobalIdCount += 1
                                val count = globalIdMap.getOrDefault(outerGlobalIdText.text, 0)
                                globalIdMap[outerGlobalIdText.text] = count + 1
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YellowText)
                        ) {
                            Text(text = "testOuterGlobalId")
                        }
                        Text(text = "() =", color = WhiteText)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = TAB_2)
                        Text(text = "Once.byGlobalId(", color = WhiteText)
                        IdInputBox(outerGlobalIdText) {
                            outerGlobalIdText = it
                        }
                        Text(text = ")", color = WhiteText)
                    }
                    Text(text = "}", color = WhiteText)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdInputBox(value: TextFieldValue, onChange: (TextFieldValue) -> Unit) {
    TextField(
        value = value,
        onValueChange = {
            if (it.text.length <= 7) {
                onChange(it)
            }
        },
        modifier = Modifier.width(80.dp),
        maxLines = 1
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FunLastReturnedText(count: Int, returnedValue: Boolean?, ranCount: Int = 0) {
    if (count > 0) {
        FlowRow {
            Text(text = TAB_1)
            Text(
                text = "// fun last returned: $returnedValue${
                    if(ranCount > 0) " | called $ranCount time(s)" else ""
                }",
                color = GrayText
            )
        }
    }
}


@Composable
fun IdsUsedColumn(map: Map<String, Int>? = null, list: List<InstanceId>? = null) {
    Column {
        map?.forEach {
            Text(text = "$TAB_1// \"${it.key}\" ran ${it.value} times", color = GrayText)
        }
        list?.forEach {
            Text(text = "$TAB_1// ${it.hash}-${it.id} ran ${it.count} times", color = GrayText)
        }
    }
}

@Composable
fun SpacingBetweenFunc() {
    Text(text = "")
    Text(text = "")
}

@Composable
fun SectionTitle(text: String) {
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(4.dp)
    )
}

@Composable
fun SectionSubtitle(text: String) {
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(4.dp)
    )
}