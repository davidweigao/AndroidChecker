package com.gaowei.checker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gaowei.checker.model.Cell;

public class CellView extends View {

    private Cell mCell;
    private Paint mBackgroundPaint = new Paint();
    private Paint mHintPaint = new Paint();
    private Paint mPiecePaint = new Paint();
    private Paint mTargetPaint = new Paint();

    public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CellView(Context context) {
        super(context);
        init();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void init() {
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAntiAlias(true);
        mHintPaint.setStyle(Paint.Style.STROKE);
        mHintPaint.setAntiAlias(true);
        mHintPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
        mHintPaint.setStrokeWidth(5);
        mPiecePaint.setStyle(Paint.Style.FILL);
        mPiecePaint.setAntiAlias(true);
        mTargetPaint.setStyle(Paint.Style.STROKE);
        mTargetPaint.setAntiAlias(true);
        mTargetPaint.setStrokeWidth(5);
        mTargetPaint.setColor(0xffff0000);
    }

    public void setCell(Cell cell) {
        mCell = cell;
        cell.setView(this);
        mBackgroundPaint.setColor(mCell.getColor());
        if (mCell.hasHint())
            mHintPaint.setColor(mCell.getHintColor());
        if (mCell.isOccupied())
            mPiecePaint.setColor(mCell.getPieceColor());
    }

    @Override
    public void invalidate() {
        if (mCell.isOccupied())
            mPiecePaint.setColor(mCell.getPieceColor());
        super.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawRect(0, 0, width, height, mBackgroundPaint);
        if (mCell.isOccupied()) {
            canvas.drawCircle(width / 2, height / 2, width * 0.4f, mPiecePaint);
            if (mCell.getPiece().isJumpTarget()) {
                canvas.drawCircle(width / 2, height / 2, width * 0.45f, mTargetPaint);
            }
        } else if (mCell.hasHint()) {
            canvas.drawCircle(width / 2, height / 2, width * 0.45f, mHintPaint);
        }
    }

}
