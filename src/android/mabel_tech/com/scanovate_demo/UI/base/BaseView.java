package mabel_tech.com.scanovate_demo.UI.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
    void onNoConnection();
}
