import Card from '@/components/Layout/Card';
import React from 'react';

interface DaySpanButtonsProps {
  setPastDaySpan: (value: number) => void;
  pastDaySpan: number;
}

const DaySpanButtons: React.FC<DaySpanButtonsProps> = ({ setPastDaySpan, pastDaySpan }) => {
  const buttons = [
    { label: "1W", value: 7 },
    { label: "2W", value: 14 },
    { label: "1M", value: 30 },
    { label: "1Y", value: 365 },
  ];

  return (
    <Card colspan={12} rowspan={1}>
        <div className="flex justify-start space-x-4">
        {buttons.map(({ label, value }) => (
            <button
            key={value}
            onClick={() => setPastDaySpan(value)}
            className={`px-4 py-2 text-sm font-medium rounded transition-colors 
                ${pastDaySpan === value ? "bg-blue-500 text-white" : "bg-gray-100 text-gray-700 hover:bg-gray-200"}`}
            >
            {label}
            </button>
        ))}
        </div>
    </Card>
  );
};

export default DaySpanButtons;
