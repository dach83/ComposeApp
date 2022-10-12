package com.dach.composeapp.expandable_card

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ExpandableCardDemo() {
    Column() {
        Spacer(modifier = Modifier.height(8.dp))

        ExpandableCard(title = "Text") {
            Text(text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
        }

        Spacer(modifier = Modifier.height(8.dp))

        ExpandableCard(
            title = "Sort order",
            iconVector = Icons.Default.Sort,
            iconRotateEnabled = false,
        ) {
            Column {
                Row {
                    TextRadioButton(text = "Title", onClick = { /*TODO*/ })
                    Spacer(modifier = Modifier.width(12.dp))
                    TextRadioButton(text = "Date", selected = true, onClick = { /*TODO*/ })
                    Spacer(modifier = Modifier.width(12.dp))
                    TextRadioButton(text = "Color", onClick = { /*TODO*/ })
                }
                Row {
                    TextRadioButton(text = "Ascending", onClick = { /*TODO*/ })
                    Spacer(modifier = Modifier.width(12.dp))
                    TextRadioButton(text = "Descending", selected = true, onClick = { /*TODO*/ })
                }
            }
        }
    }
}
