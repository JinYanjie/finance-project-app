package com.kangce.finance.ui.fixedassets;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kangce.finance.R;
import com.kangce.finance.bean.FixedAssetsEntity;

import java.text.SimpleDateFormat;

public class FixedAssetsAdapter extends BaseQuickAdapter<FixedAssetsEntity,BaseViewHolder> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FixedAssetsAdapter() {
        super(R.layout.item_fixed_assets_curtly);
    }

    @Override
    protected void convert(BaseViewHolder helper, FixedAssetsEntity item) {
        helper.setText(R.id.tv_assetsName,item.getAssetsName());
        helper.setText(R.id.tv_assetsCode,"编号: "+item.getAssetsCode());
        helper.setText(R.id.tv_inputDate,"入账时间: "+simpleDateFormat.format(item.getInputTime()));

        helper.setText(R.id.tv_cash,item.getInitialAssetValue().toString());
        switch (item.getChangeWay()){
            case 1:
                helper.setText(R.id.tv_change,"购入");
                break;
            case 2:
                helper.setText(R.id.tv_change,"接受投资");
                break;
            case 3:
                helper.setText(R.id.tv_change,"接受捐赠");
                break;
            case 4:
                helper.setText(R.id.tv_change,"自建");
                break;
            case 5:
                helper.setText(R.id.tv_change,"盘盈");
                break;
            case 6:
                helper.setText(R.id.tv_change,"出售");
                break;
            case 7:
                helper.setText(R.id.tv_change,"报废");
                break;
            case 8:
                helper.setText(R.id.tv_change,"盘亏");
                break;
            case 9:
                helper.setText(R.id.tv_change,"其他减少");
                break;
            case 10:
                helper.setText(R.id.tv_change,"其他增加");
                break;
        }

    }
}
