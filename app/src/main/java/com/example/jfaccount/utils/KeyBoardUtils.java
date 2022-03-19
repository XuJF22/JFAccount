package com.example.jfaccount.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.jfaccount.R;

public class KeyBoardUtils {
    private final Keyboard k1; //
    private KeyboardView keyboardView;
    private EditText editText;

    public interface OnEnsureListener {
        public void onEnsure();
    }

    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);//取消系统键盘
        k1 = new Keyboard(this.editText.getContext(), R.xml.key);

        this.keyboardView.setKeyboard(k1);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);

    }

    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void onPress(int primaryCode) {

        }
        @Override
        public void onRelease(int primaryCode) {

        }
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE: //删除
                    if (editable != null && editable.length() > 0){
                        if (start>0) {
                            editable.delete(start - 1,start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL: //清零
                    editable.clear();
                    break;
                case Keyboard.KEYCODE_DONE:  //完成
                    onEnsureListener.onEnsure(); //通过接口回调的方法，点击确定时调用该方法

                    break;
                default:
                    editable.insert(start,Character.toString((char)primaryCode));
                    break;
            }
        }
        @Override
        public void onText(CharSequence charSequence) {

        }
        @Override
        public void swipeLeft() {

        }
        @Override
        public void swipeRight() {

        }
        @Override
        public void swipeDown() {

        }
        @Override
        public void swipeUp() {

        }
    };

    //显示键盘的方法
    public void showKeyBoard(){
        int visibility = keyboardView.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    //隐藏键盘的方法
    public void hideKeyBoard(){
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE || visibility == View.INVISIBLE ) {
            keyboardView.setVisibility(View.GONE);
        }
    }

}
