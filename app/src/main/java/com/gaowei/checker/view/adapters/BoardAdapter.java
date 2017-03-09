package com.gaowei.checker.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gaowei.checker.R;
import com.gaowei.checker.model.Board;
import com.gaowei.checker.model.Cell;
import com.gaowei.checker.view.CellView;


public class BoardAdapter extends BaseAdapter {

    private final Board mBoard;
    private final LayoutInflater mInflater;

    public BoardAdapter(final Context context, final Board board) {
        mBoard = board;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mBoard.getSize();
    }

    @Override
    public Cell getItem(final int position) {
        int row = position / Board.LENGTH;
        int column = position % Board.LENGTH;
        return mBoard.getCell(row, column);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.griditem_cell, parent, false);
            Cell cell = getItem(position);
            ((CellView) convertView).setCell(cell);
        }
        return convertView;
    }

}
