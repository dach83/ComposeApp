package com.dach.composeapp.expandable_card

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = LocalTextStyle.current,
    selected: Boolean = false,
    onClick: (() -> Unit)?,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            colors = colors
        )
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextRadioButtonPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        TextRadioButton(text = "Selected", selected = true, onClick = { /*TODO*/ })
        TextRadioButton(text = "Unselected", selected = false, onClick = { /*TODO*/ })
    }
}