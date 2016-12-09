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

    private Board mBoard;
    private Context mContext;
    private LayoutInflater mInflater;

    public BoardAdapter(Context context, Board board) {
        mContext = context;
        mBoard = board;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mBoard.getSize();
    }

    @Override
    public Cell getItem(int position) {
        int row = position / Board.LENGTH;
        int column = position % Board.LENGTH;
        return mBoard.getCell(row, column);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.griditem_cell, parent, false);
            Cell cell = getItem(position);
            ((CellView)convertView).setCell(cell);
        }
        return convertView;
    }

}
