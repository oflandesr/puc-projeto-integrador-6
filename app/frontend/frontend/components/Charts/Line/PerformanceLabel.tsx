const PerformanceLabel: React.FC<{ start: number; end: number }> = ({ start, end }) => {
    const performanceChange = ((end - start) / start) * 100;
    const performanceClass = performanceChange > 0 ? "text-green-500" : "text-red-500";

    return (
        <div className={`flex items-center px-2.5 py-0.5 text-base font-semibold ${performanceClass}`}>
            {performanceChange.toFixed(2)}%
        </div>
    );
};

export default PerformanceLabel;