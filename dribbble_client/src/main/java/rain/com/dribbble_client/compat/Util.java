package rain.com.dribbble_client.compat;

import android.text.Html;

/**
 * Created by HwanJ.Choi on 2017-11-29.
 */

public class Util {

    public static String fromHtmlCompat(String htmlStr) {
        if (htmlStr == null)
            return "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlStr, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(htmlStr).toString();
        }
    }
}
