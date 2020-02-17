package com.catsoft.vktinA.ui.documentList

import android.content.Context
import android.opengl.Visibility
import android.os.Handler
import android.os.SystemClock
import android.text.InputType
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catsoft.vktinA.R
import com.catsoft.vktinA.utils.CalendarReadableUtil
import com.catsoft.vktinA.utils.SizeHumanReadableUtil
import com.catsoft.vktinA.vkApi.documents.model.DocumentType
import com.catsoft.vktinA.vkApi.documents.model.VKApiDocument
import java.util.*


class DocumentsListRecyclerViewAdapter(
    private val locale: Locale,
    private val viewModel: DocumentsListViewModel) : RecyclerView.Adapter<DocumentViewHolder>() {

    var documents: MutableList<VKApiDocument> = mutableListOf()

    override fun getItemCount(): Int = documents.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_document, parent, false)

        val holder = DocumentViewHolder(view)
        holder.itemView.setOnClickListener { }
        holder.dotsImageView.setOnClickListener {
            showMenuPopup(view, holder)
        }

        holder.nameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val item = documents[holder.adapterPosition]
                viewModel.renameDoc(documents[holder.adapterPosition], holder.nameEditText.text.toString() + "." + item.ext)
                holder.nameEditText.setSelection(0)
                holder.nameEditText.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                holder.nameEditText.isEnabled = false
            }
        }

        holder.nameEditText.setOnEditorActionListener { _, _, _ ->
            holder.nameEditText.clearFocus()
            true
        }

        return holder
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {

        val item = documents[position]

        var title = item.title
        if (title.endsWith("." + item.ext, true)) {
            val removeLength = 1 + item.ext.length
            title = title.removeRange(title.length - removeLength, title.length)
        }

        holder.nameEditText.setText(title)
        holder.infoTextView.text = generateInfoString(item)

        val backDrawableRes = getBackDrawable(item)
        holder.typeContainer.setBackgroundResource(backDrawableRes)

        val typeIcon = getTypeIcon(item)
        holder.typeImage.setImageResource(typeIcon)

        if (item.tags.isNotEmpty()) {
            holder.tagsContainer.visibility = View.VISIBLE
            holder.nameEditText.inputType = InputType.TYPE_CLASS_TEXT
            holder.nameEditText.maxLines = 1
        } else {
            holder.tagsContainer.visibility = View.GONE
            holder.nameEditText.maxLines = 2
        }

        val tagsText = item.tags.joinToString(", ")
        holder.tagsTextView.text = tagsText
    }

    fun updateEmployeeListItems(documents: List<VKApiDocument>) {
        val diffResult = DiffUtil.calculateDiff(DocumentsDiffCallback(documents, this.documents))
        this.documents = documents.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    private fun showMenuPopup(view: View, holder: DocumentViewHolder) {
        val popup = PopupMenu(view.context, holder.dotsImageView, Gravity.END, 0, R.style.PopupMenu)
        popup.inflate(R.menu.document_list_context_menu)
        popup.setOnMenuItemClickListener {
            val document = documents[holder.adapterPosition]
            when (it.itemId) {
                R.id.documentListContextMenu_remove -> viewModel.removeDoc(document)
                R.id.documentListContextMenu_rename -> {
                    holder.nameEditText.isEnabled = true
                    holder.nameEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
                    holder.nameEditText.requestFocus()
                    holder.nameEditText.setSelection(holder.nameEditText.text?.length ?: 0)

                    Handler().postDelayed({
                        val imm: InputMethodManager? = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                        imm?.showSoftInput(holder.nameEditText, InputMethodManager.SHOW_FORCED)
                    }, 200)
                }
            }
            true
        }
        popup.show()
    }

    private fun getBackDrawable(item: VKApiDocument): Int {
        return when (item.type) {
            DocumentType.Book -> R.drawable.type_book
            DocumentType.Audio -> R.drawable.type_audio
            DocumentType.Video -> R.drawable.type_video
            DocumentType.TextDocument -> R.drawable.type_text
            DocumentType.Zip -> R.drawable.type_zip
            else -> R.drawable.type_other
        }
    }

    private fun getTypeIcon(item: VKApiDocument): Int {
        return when (item.type) {
            DocumentType.Book -> R.drawable.ic_book_vector
            DocumentType.Audio -> R.drawable.ic_audio_vector
            DocumentType.Video -> R.drawable.ic_video_vector
            DocumentType.TextDocument -> R.drawable.ic_text_vector
            DocumentType.Zip -> R.drawable.ic_zip_vector
            else -> R.drawable.ic_other_vector
        }
    }

    private fun generateInfoString(item: VKApiDocument): String {

        val ext = item.ext.toUpperCase(locale)
        val size = SizeHumanReadableUtil.format(item.size.toLong())
        val date = CalendarReadableUtil.format(item.calendar)

        val values = if (item.ext.isNotEmpty()) listOf(ext, size, date) else listOf(date, size)

        return values.joinToString(" · ")
    }

}

