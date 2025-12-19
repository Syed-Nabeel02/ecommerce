import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { FaBoxOpen, FaDollarSign, FaShoppingCart } from 'react-icons/fa';
import DashboardOverview from './components/dashboard/DashboardOverview';
import { analyticsAction } from '../../store/actions';
import ErrorDisplay from '../../components/ui/feedback/ErrorDisplay';

const DashboardView = () => {
  const dispatch = useDispatch();
  const { errorMessage } = useSelector((state) => state.errors);
  const {
    analytics: { productCount, totalRevenue, totalOrders },
  } = useSelector((state) => state.admin);

  useEffect(() => {
    dispatch(analyticsAction());
  }, [dispatch]);

  if (errorMessage) {
    return <ErrorDisplay message={errorMessage} />;
  }

  const metricsData = [
    {
      title: 'Total Products',
      amount: productCount,
      icon: FaBoxOpen,
    },
    {
      title: 'Total Orders',
      amount: totalOrders,
      icon: FaShoppingCart,
    },
    {
      title: 'Total Revenue',
      amount: totalRevenue,
      icon: FaDollarSign,
      revenue: true,
    },
  ];

  return (
    <div className='w-full bg-white'>
      {/* Metrics Overview Cards */}
      <div className='flex md:flex-row flex-col gap-6 lg:gap-8 mt-10 px-6 py-8 bg-white rounded-xl'>
        {metricsData.map((metric) => (
          <DashboardOverview
            key={metric.title}
            title={metric.title}
            amount={metric.amount}
            Icon={metric.icon}
            revenue={metric.revenue}
          />
        ))}
      </div>
    </div>
  );
};

export default DashboardView;
