import { Icon } from '@mui/material';

const DashboardOverview = ({ title, amount, Icon: IconComponent, revenue = false }) => {
  const convertedAmount = Number(amount);

  const formatRevenueDisplay = (value) => {
    if (value >= 1000000000) return `${(value / 1000000000).toFixed(2)}B`;
    if (value >= 1000000) return `${(value / 1000000).toFixed(2)}M`;
    if (value >= 1000) return `${(value / 1000).toFixed(2)}K`;
    return value.toFixed(2);
  };

  const finalDisplayAmount = revenue ? formatRevenueDisplay(convertedAmount) : convertedAmount;

  return (
    <div className='xl:w-80 w-full space-y-4 text-center md:text-start px-8 py-10 bg-white rounded-xl shadow-sm border border-gray-100 hover:shadow-lg transition-shadow duration-200'>
      <div className='flex md:justify-start justify-center items-center gap-3'>
        <h3 className='uppercase text-sm font-semibold text-gray-600 tracking-widest'>{title}</h3>
        <IconComponent className='text-red-600 text-2xl flex-shrink-0' />
      </div>

      <h1 className='font-bold text-red-600 text-4xl leading-tight'>
        {revenue ? '$' : ''}
        {finalDisplayAmount}
      </h1>
    </div>
  );
};

export default DashboardOverview;
