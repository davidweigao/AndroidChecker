package com.gaowei.checker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.gaowei.checker.model.Board;
import com.gaowei.checker.model.BotPlayer;
import com.gaowei.checker.model.Cell;
import com.gaowei.checker.model.Game;
import com.gaowei.checker.model.GameStateListener;
import com.gaowei.checker.model.Player;
import com.gaowei.checker.view.adapters.BoardAdapter;
import com.gaowei.checker.R;

public class GameFragment extends Fragment implements AdapterView.OnItemClickListener, GameStateListener, View.OnClickListener{
    private Game mGame;
    private Board mBoard;
    private GridView boardView;
    private BoardAdapter mAdapter;
    private Player mBlackPiecePlayer;
    private Player mWhitePiecePlayer;
    private Player mCurrentPlayer;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_game, container, false);
        mBoard = new Board();
        mGame = new Game(mBoard);
        mWhitePiecePlayer = new BotPlayer();
        mBlackPiecePlayer = new Player();
        mGame.newGame(mWhitePiecePlayer, mBlackPiecePlayer,this);
        mGame.reset();
        boardView = (GridView) view.findViewById(R.id.boardGridView);
        boardView.setNumColumns(Board.LENGTH);
        mAdapter = new BoardAdapter(getActivity(), mBoard);
        boardView.setAdapter(mAdapter);
        boardView.setOnItemClickListener(this);

        view.findViewById(R.id.newGameButton).setOnClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        Cell cell = mAdapter.getItem(position);
        if(cell.isOccupied()) {
            if(mCurrentPlayer == mBlackPiecePlayer && cell.getPiece().isBlackPiece()) {
                mBlackPiecePlayer.showHint(cell);
            }
        } else if(cell.hasHint()) {
            if(mCurrentPlayer == mBlackPiecePlayer) {
                mBlackPiecePlayer.movePieceTo(cell);
            }
        }
    }

    @Override
    public void switchPlayer(final Player player) {
        mCurrentPlayer = player;
    }

    @Override
    public void onClick(final View v) {
        switch(v.getId()) {
            case R.id.newGameButton:
                gameReset();
                break;
        }
    }

    public void gameReset() {
        mGame.reset();
        boardView.setOnItemClickListener(this);
    }

    @Override
    public void gameover(final Player winner) {
        Toast.makeText(getActivity(),
                winner.equals(mBlackPiecePlayer) ? R.string.you_win : R.string.you_lost,
                Toast.LENGTH_SHORT).show();
        boardView.setOnItemClickListener(null);
    }
}
