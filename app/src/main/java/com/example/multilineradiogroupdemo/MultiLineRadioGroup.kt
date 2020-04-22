package com.example.multilineradiogroupdemo


import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.GridLayout
import android.widget.RadioButton
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi

class MultiLineRadioGroup: GridLayout {

    private var mProtectFromCheckedChange = false
    var mCheckedId = -1

    private val mChildOnCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CheckedStateTracker()
    private val mPassThroughListener: PassThroughHierarchyChangeListener = PassThroughHierarchyChangeListener()
    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null

    constructor(context: Context?): this(context, null)

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    init {
        super.setOnHierarchyChangeListener(mPassThroughListener)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is RadioButton) {
            if (child.isChecked) {
                mProtectFromCheckedChange = true
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false)
                }
                mProtectFromCheckedChange = false
                setCheckedId(child.id)
            }
        }
        super.addView(child, index, params)
    }

    fun check(@IdRes id: Int) { // don't even bother
        if (id != -1 && id == mCheckedId) {
            return
        }
        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false)
        }
        if (id != -1) {
            setCheckedStateForView(id, true)
        }
        setCheckedId(id)
    }

    private fun setCheckedId(@IdRes id: Int) {
        val changed = id != mCheckedId
        mCheckedId = id
        mOnCheckedChangeListener?.onCheckedChanged(this, mCheckedId)
//        if (changed) {
//            val afm: AutofillManager = mContext.getSystemService(
//                AutofillManager::class.java
//            )
//            afm?.notifyValueChanged(this)
//        }
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null && checkedView is RadioButton) {
            checkedView.isChecked = checked
        }
    }

    private inner  class CheckedStateTracker : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(
            buttonView: CompoundButton,
            isChecked: Boolean
        ) { // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return
            }
            mProtectFromCheckedChange = true
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false)
            }
            mProtectFromCheckedChange = false
            val id = buttonView.id
            setCheckedId(id)
        }
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        mOnCheckedChangeListener = listener
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(group: MultiLineRadioGroup?, @IdRes checkedId: Int)
    }

    private inner class PassThroughHierarchyChangeListener :
        OnHierarchyChangeListener {
        private val mOnHierarchyChangeListener: OnHierarchyChangeListener? = null
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        override fun onChildViewAdded(
            parent: View,
            child: View
        ) {
            if (parent == this@MultiLineRadioGroup && child is RadioButton) {
                var id = child.getId()
                // generates an id if it's missing
                if (id == View.NO_ID) {
                    id = View.generateViewId()
                    child.setId(id)
                }
                child.setOnCheckedChangeListener(
                    mChildOnCheckedChangeListener
                )
            }
            mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        /**
         * {@inheritDoc}
         */
        override fun onChildViewRemoved(parent: View, child: View) {
            if (parent == this@MultiLineRadioGroup && child is RadioButton) {
                child.setOnCheckedChangeListener(null)
            }
            mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }

}