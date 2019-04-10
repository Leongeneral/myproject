package com.auto.chishan.manager.ui.project;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ContractBean;
import com.my.commonlibrary.base.BaseResultActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2019/3/13
 * desc:
 *
 * @author:DingZhixiang
 */
public class AddContractActivity extends BaseResultActivity {
    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.contractNameEt)
    EditText contractNameEt;
    @BindView(R.id.contractTxtEt)
    EditText contractTxtEt;
    @BindView(R.id.contractNoEt)
    EditText contractNoEt;
    @BindView(R.id.contractNumEt)
    EditText contractNumEt;

    private boolean isChange;
    private ContractBean contractBean;
    @Override
    protected int setLayout() {
        return R.layout.activity_add_contract;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("添加合同");
        saveBtn.setText("添加");
        isChange = getIntent().getBooleanExtra("isChange",false);
        if(isChange){
            saveBtn.setVisibility(View.VISIBLE);
        }else{
            saveBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        contractBean = getIntent().getParcelableExtra("bean");
        if(contractBean != null){
            contractNameEt.setText(contractBean.getContractName());
            contractTxtEt.setText(contractBean.getContractTxt());
            contractNoEt.setText(contractBean.getContractNo());
            contractNumEt.setText(contractBean.getContractNum());
        }else{
            contractBean = new ContractBean();
        }
    }

    @OnClick(R.id.saveBtn)
    public void onViewClicked() {
        contractBean.setContractName(contractNameEt.getText().toString().trim());
        contractBean.setContractNo(contractNoEt.getText().toString().trim());
        contractBean.setContractNum(contractNumEt.getText().toString().trim());
        contractBean.setContractTxt(contractTxtEt.getText().toString().trim());
        Intent intent = new Intent();
        intent.putExtra("bean", contractBean);
        setResult(1001,intent);
        finish();
    }

}
