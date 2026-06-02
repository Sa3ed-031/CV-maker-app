package com.example.cvbuilder.ui.screens

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cvbuilder.R
import com.example.cvbuilder.data.models.CVData
import com.example.cvbuilder.navigation.Screen
import com.example.cvbuilder.ui.components.CustomButton
import com.example.cvbuilder.ui.components.SuccessDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(navController: NavHostController, cvData: CVData) {
    var isClassicTemplate by remember { mutableStateOf(true) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showSuccessDialog) {
        SuccessDialog(onDismiss = {
            showSuccessDialog = false
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Dashboard.route) { inclusive = true }
            }
        })
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.preview_title)) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Dynamic UI Layout Template Toggle Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.classic_template))
                Switch(
                    checked = !isClassicTemplate,
                    onCheckedChange = { isClassicTemplate = !it },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Text(stringResource(R.string.modern_template))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CV Canvas Container Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(24.dp)
            ) {
                if (isClassicTemplate) {
                    ClassicTemplateLayout(cvData)
                } else {
                    ModernSidebarTemplateLayout(cvData)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = stringResource(R.string.export_pdf),
                onClick = {
                    exportCvToPdf(context, cvData, isClassicTemplate) { success ->
                        if (success) showSuccessDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// =============================================================================
// INNOVATION FEATURE 1: DYNAMIC LAYOUT TEMPLATES
// =============================================================================

@Composable
fun ClassicTemplateLayout(cvData: CVData) {
    Column {
        Text(text = cvData.fullName, style = MaterialTheme.typography.headlineLarge, color = Color.Black)
        Text(text = "${cvData.email} | ${cvData.phone}", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = cvData.summary, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Experience", style = MaterialTheme.typography.titleLarge, color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "${cvData.jobTitle} at ${cvData.company}", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Education", style = MaterialTheme.typography.titleLarge, color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "${cvData.degree} — ${cvData.institution}", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Skills", style = MaterialTheme.typography.titleLarge, color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = cvData.skills.joinToString(", "), style = MaterialTheme.typography.bodyLarge, color = Color.Black)
    }
}

@Composable
fun ModernSidebarTemplateLayout(cvData: CVData) {
    Row(modifier = Modifier.fillMaxSize()) {

        // Left Structural Color Sidebar
        Column(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
                .background(Color(0xFF2C3E50))
                .padding(12.dp)
        ) {
            Text(text = "Contact", style = MaterialTheme.typography.titleLarge, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = cvData.phone, style = MaterialTheme.typography.labelMedium, color = Color.LightGray)
            Text(text = cvData.email, style = MaterialTheme.typography.labelMedium, color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Skills", style = MaterialTheme.typography.titleLarge, color = Color.White)
            cvData.skills.forEach { skill ->
                Text(text = "• $skill", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        // Right Main Dynamic Content Area
        Column(modifier = Modifier.weight(0.6f)) {
            Text(text = cvData.fullName, style = MaterialTheme.typography.headlineLarge, color = Color(0xFF2C3E50), fontWeight = FontWeight.Bold)
            Text(text = cvData.jobTitle, style = MaterialTheme.typography.titleLarge, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = cvData.summary, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Work Experience", style = MaterialTheme.typography.titleLarge, color = Color(0xFF2C3E50), fontWeight = FontWeight.Bold)
            Text(text = cvData.company, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Education background", style = MaterialTheme.typography.titleLarge, color = Color(0xFF2C3E50), fontWeight = FontWeight.Bold)
            Text(text = cvData.degree, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
            Text(text = cvData.institution, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        }
    }
}

// =============================================================================
// INNOVATION FEATURE 2: NATIVE ANDROID PDF EXPORT HANDLER
// =============================================================================

private fun exportCvToPdf(context: Context, cvData: CVData, isClassic: Boolean, onResult: (Boolean) -> Unit) {
    val pdfDocument = PdfDocument()
    // Standard Letter Size Page Dimensions (8.5 x 11 inches at 72 DPI)
    val pageInfo = PdfDocument.PageInfo.Builder(612, 792, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas: Canvas = page.canvas

    val paint = Paint()
    val titlePaint = Paint().apply {
        isAntiAlias = true
        textSize = 24f
        // الحل: تم استبدال fontWeight الخاطئة بـ typeface الصحيحة لكلاس Paint
        typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
    }
    val bodyPaint = Paint().apply {
        isAntiAlias = true
        textSize = 14f
    }

    if (isClassic) {
        // Classic Native Canvas Drawing Logic Engine
        titlePaint.color = android.graphics.Color.BLACK
        canvas.drawText(cvData.fullName, 50f, 80f, titlePaint)

        bodyPaint.color = android.graphics.Color.GRAY
        canvas.drawText("${cvData.email} | ${cvData.phone}", 50f, 110f, bodyPaint)

        canvas.drawLine(50f, 130f, 562f, 130f, paint)

        bodyPaint.color = android.graphics.Color.BLACK
        canvas.drawText(cvData.summary, 50f, 160f, bodyPaint)

        canvas.drawText("Experience: ${cvData.jobTitle} at ${cvData.company}", 50f, 210f, bodyPaint)
        canvas.drawText("Education: ${cvData.degree} at ${cvData.institution}", 50f, 260f, bodyPaint)
        canvas.drawText("Skills: ${cvData.skills.joinToString(", ")}", 50f, 310f, bodyPaint)
    } else {
        // Modern Native Canvas Document Rendering Engine Layout Structure
        // الحل: تم إصلاح تحويل اللون من String إلى Int لتجنب تحذير الـ KTX extension
        paint.color = android.graphics.Color.parseColor("#2C3E50")
        canvas.drawRect(0f, 0f, 200f, 792f, paint)

        titlePaint.color = android.graphics.Color.WHITE
        canvas.drawText("Contact", 20f, 80f, titlePaint)

        bodyPaint.color = android.graphics.Color.LTGRAY
        canvas.drawText(cvData.phone, 20f, 120f, bodyPaint)
        canvas.drawText(cvData.email, 20f, 140f, bodyPaint)

        titlePaint.color = android.graphics.Color.parseColor("#2C3E50")
        canvas.drawText(cvData.fullName, 220f, 80f, titlePaint)

        bodyPaint.color = android.graphics.Color.BLACK
        canvas.drawText(cvData.jobTitle, 220f, 110f, bodyPaint)
        canvas.drawText(cvData.summary, 220f, 150f, bodyPaint)
        canvas.drawText("Company: ${cvData.company}", 220f, 220f, bodyPaint)
        canvas.drawText("Degree: ${cvData.degree}", 220f, 280f, bodyPaint)
    }

    pdfDocument.finishPage(page)

    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(directory, "${cvData.fullName.replace(" ", "_")}_CV.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        onResult(true)
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Failed to generate file: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        onResult(false)
    } finally {
        pdfDocument.close()
    }
}