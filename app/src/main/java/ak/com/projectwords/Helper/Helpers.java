package ak.com.projectwords.Helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ak.com.projectwords.Adapters.DictionaryAdapter;

/**
 * Created by artyomkuznetsov on 2/20/18.
 */

public class Helpers {
    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        TextView v = toast.getView().findViewById(android.R.id.message);
        if(v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public static void hideKeyboard(View view, Activity activity) {
        InputMethodManager inputMethodManager =(InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void setVisibilityOfProgressBar(final int type, Activity activity, final ProgressBar view) {
       if(activity != null) {
           Runnable mRunnable = () -> {
               if (type == View.GONE) {
                   view.setVisibility(View.GONE);
               } else if (type == View.VISIBLE) {
                   view.setVisibility(View.VISIBLE);
               }
           };
           activity.runOnUiThread(mRunnable);
       }
    }

    public static String setCapitalLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }

    public static ItemTouchHelper createItemTouchHelper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(position);
                if(adapter instanceof DictionaryAdapter) {
                    ((DictionaryAdapter)adapter).remove(position);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

        return itemTouchHelper;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
