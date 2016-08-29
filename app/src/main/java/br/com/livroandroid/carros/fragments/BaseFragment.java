package br.com.livroandroid.carros.fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class BaseFragment extends Fragment {

    private static final String TAG = "livroandroid";

    protected void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT ).show();
    }

    public void startTask(TaskListener listener) {
        Task task = new Task(listener);
        task.execute();
    }

    public class Task extends AsyncTask<Void, Void, TaskResult> {
        TaskListener listener;

        private Task(TaskListener listener) {
            this.listener = listener;
        }

        @Override
        protected TaskResult doInBackground(Void... params) {
            TaskResult result = new TaskResult();
            try {
                result.response = listener.execute();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                result.exception = e;
            }
            return result;
        }

        @Override
        protected void onPostExecute(TaskResult taskResult) {
            super.onPostExecute(taskResult);
            listener.updateView(taskResult.response);
        }
    }

    public interface TaskListener<T> {
        // Executa em background e retorna o objeto
        T execute() throws Exception;

        // Atualiza a view na UI Thread
        void updateView(T response);

        // Chamado caso o método execute() lance uma exception
        void onError(Exception exception);

        // Chamado caso a task tenha sido cancelada
        void onCancelled(String cod);
    }

    private class TaskResult<T> {
        private T response;
        private Exception exception;
    }




}
