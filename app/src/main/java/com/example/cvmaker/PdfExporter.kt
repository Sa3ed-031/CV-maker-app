package com.example.cvmaker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PdfExporter {

    fun exportCvToPdf(
        context: Context,
        fullName: String,
        phone: String,
        nationalId: String,
        university: String,
        specialty: String,
        gradYear: String,
        experience: String,
        skills: String,
        languages: String
    ) {
        // 1. إنشاء كائن الـ PDF وتحديد أبعاد الصفحة (A4 القياسية: 595 عرض × 842 طول بيكسل)
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        // 2. تجهيز أدوات الرسم والألوان (الهوية البصرية)
        val paint = Paint()

        // الألوان بصيغة Hex المتناسقة مع التطبيق
        val greenPrimaryColor = Color.parseColor("#4CAF50") // الأخضر الرسمي
        val backgroundGrayColor = Color.parseColor("#F5F5F5") // الرمادي الفاتح
        val textDarkColor = Color.parseColor("#212121") // الخط الغامق الواضح

        // 3. رسم العمود الجانبي الملون (العمود الضيق - العرض 200 بيكسل)
        paint.color = backgroundGrayColor
        canvas.drawRect(0f, 0f, 200f, 842f, paint)

        // 4. خط فاصل عمودي أنيق بين العمودين بلون أخضر رفيع
        paint.color = greenPrimaryColor
        paint.strokeWidth = 3f
        canvas.drawLine(200f, 0f, 200f, 842f, paint)

        // ----------------------------------------------------------------------
        // 🟢 كتابة محتويات العمود الجانبي (البيانات الشخصية واللغات)
        // ----------------------------------------------------------------------
        paint.isAntiAlias = true
        paint.color = textDarkColor

        // الاسم الكامل (عنوان كبير في الأعلى)
        paint.textSize = 16f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText(fullName, 15f, 50f, paint)

        // خطوط التواصل والمعلومات الشخصية
        paint.textSize = 11f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("الهاتف: $phone", 15f, 90f, paint)
        canvas.drawText("الرقم الوطني: $nationalId", 15f, 115f, paint)

        // قسم اللغات (جوات العمود الجانبي)
        paint.color = greenPrimaryColor
        paint.textSize = 13f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("اللغات المتقنة", 15f, 180f, paint)

        paint.color = textDarkColor
        paint.textSize = 11f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        // تقسيم اللغات لأسطر إذا كتبت بفواصل
        var langY = 205f
        languages.split("،", ",", "\n").forEach { lang ->
            if (lang.trim().isNotEmpty()) {
                canvas.drawText("• ${lang.trim()}", 15f, langY, paint)
                langY += 20f
            }
        }

        // ----------------------------------------------------------------------
        // ⚪ كتابة محتويات العمود الرئيسي العريض (المؤهلات والخبرات والمهارات)
        // ----------------------------------------------------------------------
        val startX = 220f // إحداثيات بدء الكتابة بالعمود العريض لترك مسافة أمان

        // القسم الأكاديمي
        paint.color = greenPrimaryColor
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("المؤهلات الأكاديمية", startX, 50f, paint)

        paint.color = Color.LTGRAY
        canvas.drawLine(startX, 60f, 570f, 60f, paint) // خط أفقي فاصل تحت العنوان
        paint.color = textDarkColor
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("المؤسسة: $university", startX, 85f, paint)

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("التخصص: $specialty", startX, 110f, paint)
        canvas.drawText("سنة التخرج: $gradYear", startX, 135f, paint)

        // قسم الخبرات المهنية
        paint.color = greenPrimaryColor
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("الخبرات المهنية", startX, 185f, paint)

        paint.color = Color.LTGRAY
        canvas.drawLine(startX, 195f, 570f, 195f, paint)

        paint.color = textDarkColor
        paint.textSize = 11f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        var expY = 220f
        val expText = if (experience.trim().isEmpty()) "لا توجد خبرات سابقة" else experience
        expText.split("\n").forEach { line ->
            canvas.drawText(line, startX, expY, paint)
            expY += 20f
        }

        // قسم المهارات التقنية
        paint.color = greenPrimaryColor
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("المهارات التقنية", startX, expY + 20f, paint)

        paint.color = Color.LTGRAY
        canvas.drawLine(startX, expY + 30f, 570f, expY + 30f, paint)

        paint.color = textDarkColor
        paint.textSize = 11f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        var skillY = expY + 55f
        skills.split("،", ",", "\n").forEach { skill ->
            if (skill.trim().isNotEmpty()) {
                canvas.drawText("✔ ${skill.trim()}", startX, skillY, paint)
                skillY += 20f
            }
        }

        // 5. إنهاء الصفحة وإغلاق مستند الـ PDF
        pdfDocument.finishPage(page)

        // 6. حفظ الملف في مجلد Downloads الخاص بجهاز المستخدم
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, "CVMaker_${nationalId}.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, context.getString(R.string.toast_pdf_success), Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, context.getString(R.string.toast_pdf_error), Toast.LENGTH_LONG).show()
        } finally {
            pdfDocument.close()
        }
    }
}