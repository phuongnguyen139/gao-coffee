import React, { useState } from 'react';
import { Table, InputNumber, Typography, Image } from 'antd';

const { Title } = Typography;

// Mảng chứa các mệnh giá
const denominations = [1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000];

const Cash = () => {
    // Tạo dữ liệu ban đầu từ mảng denominations
    const [data, setData] = useState(
        denominations.map((denomination, index) => ({
            key: `${index + 1}`,
            denomination,
            quantity: 0,
            sum: 0,
        }))
    );

    // Xử lý khi thay đổi số lượng
    const handleQuantityChange = (value, record) => {
        const newData = data.map(item => {
            if (item.key === record.key) {
                return { ...item, quantity: value || 0, sum: (value || 0) * item.denomination };
            }
            return item;
        });
        setData(newData);
    };

    // Tính tổng cộng
    const totalSum = data.reduce((acc, item) => acc + item.sum, 0);

    const columns = [
        {
            title: 'Mệnh giá (VND)',
            dataIndex: 'denomination',
            key: 'denomination',
            render: text => text.toLocaleString('vi-VN'),
        },
        {
            title: 'Hình ảnh',
            dataIndex: 'denomination',
            key: 'image',
            render: denomination => (
                <Image
                    src={`/img/cash/${denomination}_dong_viet_nam.jpg`}
                    width={100}
                    alt={`Mệnh giá ${denomination}`}
                    fallback="https://via.placeholder.com/50" // Ảnh mặc định nếu không tìm thấy
                />
            ),
        },
        {
            title: 'Số lượng',
            dataIndex: 'quantity',
            key: 'quantity',
            render: (text, record) => (
                <InputNumber
                    min={0}
                    value={record.quantity}
                    onChange={value => handleQuantityChange(value, record)}
                />
            ),
        },
        {
            title: 'Tổng (VND)',
            dataIndex: 'sum',
            key: 'sum',
            render: text => text.toLocaleString('vi-VN'),
        },
        {
            title: 'Tổng cộng (VND)',
            key: 'totalSum',
            width: 200,
            render: (text, record, index) => {
                return {
                    children: (
                        <span style={{ fontWeight: 'bold', color: '#000', textAlign: 'center' }}>
                            {totalSum.toLocaleString('vi-VN')} VNĐ
                        </span>
                    ),
                    props: {
                        rowSpan: index === 0 ? data.length : 0,
                    },
                };
            },
        },
    ];

    return (
        <div>
            <Title level={3}>Doanh thu tiền mặt</Title>
            <div
                style={{
                    maxHeight: '100vh', 
                    overflowY: 'auto', 
                }}
            >
                <Table
                    columns={columns}
                    dataSource={data}
                    pagination={false}
                    size="small"
                    style={{ fontSize: '12px' }}
                />
            </div>
        </div>
    );
};

export default Cash;