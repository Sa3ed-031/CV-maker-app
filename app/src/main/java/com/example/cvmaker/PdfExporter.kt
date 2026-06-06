package com.example.cvmaker

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.OutputStream

object PdfExporter {

    // الدالة الرئيسية لاستقبال البيانات وتوليد الـ PDF
    fun saveCv(context: Context, isTwoColumn: Boolean, viewModel: CvViewModel) {
        val pdfDocument = PdfDocument()

        // قياسات الصفحة القياسية A4 بالنقاط (595 عرض × 842 ارتفاع)
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        val paint = Paint().apply { isAntiAlias = true }

        // 🎨 الألوان المخصصة للهوية البصرية للـ PDF
        val greenPrimary = Color.parseColor("#4CAF50")
        val bgGray = Color.parseColor("#F5F5F5")
        val textDark = Color.parseColor("#212121")

        // فحص خيار الـ Toggle لرسم الشكل المطلوب بالـ Canvas
        if (isTwoColumn) {
            // رسم نمط العمودين
            drawTwoColumnLayout(canvas, paint, viewModel, greenPrimary, bgGray, textDark)
        } else {
            // رسم النمط الكلاسيكي الطولي
            drawClassicLayout(canvas, paint, viewModel, greenPrimary, textDark)
        }

        pdfDocument.finishPage(page)

        // حفظ الملف بشكل متوافق وآمن عبر الـ MediaStore ومجلد الـ Downloads
        saveFileToDownloads(context, pdfDocument, viewModel.nationalId)
    }

    // دالة رسم نظام العمودين
    private fun drawTwoColumnLayout(canvas: Canvas, paint: Paint, viewModel: CvViewModel, primaryColor: Int, bgColors: Int, textColor: Int) {
        // خلفية العمود الجانبي (الأيسر أو الأيمن حسب التنسيق)
        paint.color = bgColors
        canvas.drawRect(0f, 0f, 200f, 842f, paint)

        paint.color = primaryColor
        canvas.drawLine(200f, 0f, 200f, 842f, paint)

        // رسم النصوص الأساسية
        paint.color = textColor
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText(viewModel.fullName, 15f, 50f, paint)

        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("الهاتف: ${viewModel.phone}", 15f, 80f, paint)

        // قسم المشاريع والإنجازات بالعمود الواسع
        paint.color = primaryColor
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("المشاريع والإنجازات", 215f, 50f, paint)

        paint.color = textColor
        paint.textSize = 11f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText(viewModel.projects, 215f, 75f, paint)
    }

    // دالة رسم النمط الكلاسيكي التقليدي
    private fun drawClassicLayout(canvas: Canvas, paint: Paint, viewModel: CvViewModel, primaryColor: Int, textColor: Int) {
        paint.color = primaryColor
        paint.textSize = 20f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText(viewModel.fullName, 30f, 60f, paint)

        paint.color = textColor
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("الهاتف: ${viewModel.phone} | الرقم الوطني: ${viewModel.nationalId}", 30f, 85f, paint)
        canvas.drawLine(30f, 100f, 565f, 100f, paint)

        paint.color = primaryColor
        canvas.drawText("المشاريع والإنجازات:", 30f, 130f, paint)
        paint.color = textColor
        canvas.drawText(viewModel.projects, 30f, 155f, paint)
    }

    // دالة الحفظ المتوافق في مجلد ال Downloads (حل مشكلة الـ API القديم والحديث)
    private fun saveFileToDownloads(context: Context, pdfDocument: PdfDocument, id: String) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "CVMaker_${id}.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        }

        val uri: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = java.io.File(downloadsDir, "CVMaker_${id}.pdf")
            contentValues.put(MediaStore.MediaColumns.DATA, file.absolutePath)
            resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
        }

        if (uri != null) {
            try {
                val outputStream: OutputStream? = resolver.openOutputStream(uri)
                if (outputStream != null) {
                    pdfDocument.writeTo(outputStream)
                    outputStream.close()
                    Toast.makeText(context, context.getString(R.string.toast_pdf_success), Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, context.getString(R.string.toast_pdf_error), Toast.LENGTH_LONG).show()
            } finally {
                pdfDocument.close()
            }
        } else {
            Toast.makeText(context, context.getString(R.string.toast_pdf_error), Toast.LENGTH_LONG).show()
        }
    }
}