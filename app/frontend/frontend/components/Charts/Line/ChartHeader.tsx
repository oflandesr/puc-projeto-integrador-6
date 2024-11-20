const ChartHeader: React.FC<{ title: string; latestValue: number; startDate: string; endDate: string, symbol: string }> = ({ title, latestValue, startDate, endDate, symbol }) => (
    <div>
        <h5 className="leading-none text-3xl font-bold text-gray-900 dark:text-white pb-2">{latestValue} {symbol}</h5>
        <p className="text-base font-normal text-gray-500 dark:text-gray-400">{title} {startDate} - {endDate}</p>
    </div>
);

export default ChartHeader;