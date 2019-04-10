package com.auto.chishan.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : lyzhang3
 * date   : 2019/3/218:19 PM
 * desc   :
 * {
 * 	"code": 1,
 * 	"message": "",
 * 	"data": {
 * 		"count": 1,
 * 		"arrData": [{
 * 			"id": "fab62045d76a4fc5973cf0cd74acdf1f",
 * 			"remarks": "",
 * 			"createDate": "2019-04-10 00:00:00",
 * 			"updateDate": "2019-04-10 00:00:00",
 * 			"code": "100084",
 * 			"user": {
 * 				"id": "071a700a86b84a5397fe3621efb04986",
 * 				"name": "客户测试",
 * 				"loginFlag": "1",
 * 				"admin": false,
 * 				"roleNames": "",
 * 				"officeIds": ""
 *                        },
 * 			"customer": {
 * 				"id": "bff50e83125f4ca399e0c8675d007669",
 * 				"code": "100000026",
 * 				"name": "张礼耀",
 * 				"phone": "18055177023",
 * 				"certificatesNumber": "342422199103095294",
 * 				"sex": "MALE",
 * 				"channel": "3"
 *            },
 * 			"estates": {
 * 				"id": "c08f184fb9684d1e921f1354f8ecd1bc",
 * 				"code": "100000040",
 * 				"number": "1703",
 * 				"company": {
 * 					"id": "d7639b5d469a48ffb4377e6fc50c6d58",
 * 					"name": "赤山集团有限公司房地产开发分公司",
 * 					"type": "2",
 * 					"parentId": "0"
 *                },
 * 				"state": "2",
 * 				"community": "复兴家园",
 * 				"buildingArea": 80.0000,
 * 				"estatesArea": 80.0000,
 * 				"areaDifference": 0.0000,
 * 				"price": 8600.0000,
 * 				"amount": 688000.0000,
 * 				"dealPrice": 8500.0000,
 * 				"dealAmount": 680000.0000,
 * 				"discount": 8000.0000
 *            },
 * 			"company": {
 * 				"id": "d7639b5d469a48ffb4377e6fc50c6d58",
 * 				"name": "赤山集团有限公司房地产开发分公司",
 * 				"type": "2",
 * 				"parentId": "0"
 *            },
 * 			"channel": "3",
 * 			"state": "INACTIVE",
 * 			"initialDue": "204000.0000",
 * 			"initialPaid": "0.0000",
 * 			"initialBalance": "204000.0000",
 * 			"initialRepayment": "0.0000",
 * 			"initialDate": "2019-04-30 00:00:00",
 * 			"initialState": "unsettled",
 * 			"downpaymentDue": "136000.0000",
 * 			"downpaymentPaid": "0.0000",
 * 			"downpaymentBalance": "136000.0000",
 * 			"amount": "476000.0000",
 * 			"periods": 30,
 * 			"intervalTime": 1,
 * 			"intervalYear": 2,
 * 			"repaymentType": "1",
 * 			"chargeInterest": "1",
 * 			"rateType": "FIXED",
 * 			"rateFloat": "0.0000",
 * 			"rate": "10.0000",
 * 			"penaltyMethod": "FIXEDAMOUNT",
 * 			"initialRepaymentDate": "2019-04-30 00:00:00",
 * 			"fixedDate": 1,
 * 			"subscriptionDate": 1556467200000,
 * 			"amountPaid": "0.0000",
 * 			"amountBalance": "0.0000",
 * 			"interestDue": "0.0000",
 * 			"interestPaid": "0.0000",
 * 			"interestBalance": "0.0000",
 * 			"penaltyDue": "0.0000",
 * 			"penaltyPaid": "0.0000",
 * 			"penaltyBalance": "0.0000",
 * 			"isSign": "0"        * 		}],
 * 		"totalPage": 1
 * 	}
 * }
 *
 *
 */
public class InstallmentBean implements Parcelable {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String community;

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    private String number;
    private String amount;

    public InstallmentBean(){

    }


    public InstallmentBean(Parcel in) {
        amount = in.readString();
        number = in.readString();
        community=in.readString();
        id=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(number);
        dest.writeString(community);
        dest.writeString(id);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InstallmentBean> CREATOR = new Creator<InstallmentBean>() {
        @Override
        public InstallmentBean createFromParcel(Parcel in) {
            return new InstallmentBean(in);
        }

        @Override
        public InstallmentBean[] newArray(int size) {
            return new InstallmentBean[size];
        }
    };


}
