package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ContractBean;
import com.my.commonlibrary.adapter.SelectAdapter;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.bean.SelectBean;
import com.my.commonlibrary.view.poplayout.PopupLayout;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2019/3/13
 * desc:
 *
 * @author:DingZhixiang
 */
public class AddBankCardActivity extends BaseResultActivity {
    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.id_card_number)
    EditText idCardNumber;
    @BindView(R.id.bank_card_number)
    EditText bankCardNumber;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.verify_code)
    EditText verify_code;


    private boolean isChange;
    private ContractBean contractBean;
    @Override
    protected int setLayout() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("添加合同");
        titlerightTv.setText("保存");
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

            contractBean = new ContractBean();

        View typeView = View.inflate(mActivity, R.layout.include_onlyrv, null);
        selectBeans = new ArrayList<>();
        selectAdapter = new SelectAdapter(mActivity, selectBeans, R.layout.item_tv_center);

        popupLayout = PopupLayout.init(mActivity, typeView);
        popupLayout.setHeight(300, true);
        popupLayout.setUseRadius(false);
        selectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                switch (selectType) {
                    case 1:
//                        contractTypeTv.setText(selectAdapter.getData().get(position).getName());
//                        contractType = selectAdapter.getData().get(position).getCode();
                        break;
                    case 2:
//                        flowTv.setText(selectAdapter.getData().get(position).getLabel());
//                        flow = selectAdapter.getData().get(position).getValue();
                        break;
                }
                popupLayout.dismiss();
            }
        });
    }

    private int selectType = 1;
    private SelectAdapter selectAdapter;

    private List<SelectBean> selectBeans;

    private PopupLayout popupLayout;

    @OnClick({R.id.saveBtn,R.id.title_right,R.id.select_bank,R.id.get_verify_code})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.saveBtn:
            case R.id.title_right:
//                contractBean.setContractName(contractNameEt.getText().toString().trim());
//                contractBean.setContractNo(contractNoEt.getText().toString().trim());
//                contractBean.setContractNum(contractNumEt.getText().toString().trim());
//                contractBean.setContractTxt(contractTxtEt.getText().toString().trim());
                Intent intent = new Intent();
                intent.putExtra("bean", contractBean);
                setResult(1001,intent);
                finish();
                break;
            case R.id.select_bank:
                selectType = 1;
                getContractType();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.get_verify_code:

                break;
        }

    }

    /**
     * 合同类型
     */
    private void getContractType() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setLabel("草拟合同");
        beanAccount1.setValue("true");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setLabel("格式合同");
        beanAccount2.setValue("false");
        selectBeans.add(beanAccount2);
    }

}
