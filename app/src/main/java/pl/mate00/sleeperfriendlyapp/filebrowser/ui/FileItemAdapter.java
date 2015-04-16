package pl.mate00.sleeperfriendlyapp.filebrowser.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.filebrowser.SelectableItem;

public class FileItemAdapter extends ArrayAdapter<SelectableItem> {

    private Context context;
    private int rowLayoutResourceId;
    private List<SelectableItem> items;

    public FileItemAdapter(Context context, int resource, List<SelectableItem> items) {
        super(context, resource, items);
        this.context = context;
        this.rowLayoutResourceId = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FileItemHolder holder;

        if (row == null) {
            row = View.inflate(context, rowLayoutResourceId, null);
            holder = new FileItemHolder();
            holder.cbIsSelected = (CheckBox) row.findViewById(R.id.is_file_selected);
            holder.txtFileName = (TextView) row.findViewById(R.id.txt_file_name);

            row.setTag(holder);
        } else {
            holder = (FileItemHolder) row.getTag();
        }

        SelectableItem item = items.get(position);
        holder.cbIsSelected.setVisibility(item.isSelectedVisible() ? View.VISIBLE : View.GONE);
        holder.cbIsSelected.setChecked(item.isSelected());
        holder.txtFileName.setText(item.getFileName());

        return row;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    static class FileItemHolder {
        TextView txtFileName;
        CheckBox cbIsSelected;
    }
}
