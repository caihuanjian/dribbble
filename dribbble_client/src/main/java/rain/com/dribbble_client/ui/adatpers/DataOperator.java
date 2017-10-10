package rain.com.dribbble_client.ui.adatpers;

import java.util.List;

/**
 * Created by HwanJ.Choi on 2017-5-18.
 */

public interface DataOperator<T> {

    void updateData(List<T> list);

    void appendData(List<T> list);
}
