'use strict';

const ReactDOM = require("pug");

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

const Heading = function Heading(_ref) {
    const date = _ref.date;
    const changeMonth = _ref.changeMonth;
    const resetDate = _ref.resetDate;
    return React.createElement(
        'nav',
        {className: 'calendar--nav'},
        React.createElement(
            'a',
            {
                onClick: function onClick() {
                    return changeMonth(date.month() - 1);
                }
            },
            '‹'
        ),
        React.createElement(
            'h1',
            {
                onClick: function onClick() {
                    return resetDate();
                }
            },
            date.format('MMMM'),
            ' ',
            React.createElement(
                'small',
                null,
                date.format('YYYY')
            )
        ),
        React.createElement(
            'a',
            {
                onClick: function onClick() {
                    return changeMonth(date.month() + 1);
                }
            },
            '›'
        )
    );
};

const Day = function Day(_ref2) {
    const currentDate = _ref2.currentDate;
    const date = _ref2.date;
    const startDate = _ref2.startDate;
    const endDate = _ref2.endDate;
    const _onClick = _ref2.onClick;

    const className = [];

    if (moment().isSame(date, 'day')) {
        className.push('active');
    }

    if (date.isSame(startDate, 'day')) {
        className.push('start');
    }

    if (date.isBetween(startDate, endDate, 'day')) {
        className.push('between');
    }

    if (date.isSame(endDate, 'day')) {
        className.push('end');
    }

    if (!date.isSame(currentDate, 'month')) {
        className.push('muted');
    }

    return React.createElement(
        'span',
        {
            onClick: function onClick() {
                return _onClick(date);
            }, currentDate: date, className: className.join(' ')
        },
        date.date()
    );
};

const Days = function Days(_ref3) {
    let i;
    const date = _ref3.date;
    const startDate = _ref3.startDate;
    const endDate = _ref3.endDate;
    const _onClick2 = _ref3.onClick;

    const thisDate = moment(date);
    const daysInMonth = moment(date).daysInMonth();
    const firstDayDate = moment(date).startOf('month');
    const previousMonth = moment(date).subtract(1, 'month');
    const previousMonthDays = previousMonth.daysInMonth();
    const nextsMonth = moment(date).add(1, 'month');
    const days = [];
    const labels = [];

    for (i = 1; i <= 7; i++) {
        labels.push(React.createElement(
            'span',
            {className: 'label'},
            moment().day(i).format('ddd')
        ));
    }

    for (i = firstDayDate.day(); i > 1; i--) {
        previousMonth.date(previousMonthDays - i + 2);

        days.push(React.createElement(Day, {
            key: moment(previousMonth).format('DD MM YYYY'), onClick: function onClick(date) {
                return _onClick2(date);
            }, currentDate: date, date: moment(previousMonth), startDate: startDate, endDate: endDate
        }));
    }

    for (i = 1; i <= daysInMonth; i++) {
        thisDate.date(i);

        days.push(React.createElement(Day, {
            key: moment(thisDate).format('DD MM YYYY'), onClick: function onClick(date) {
                return _onClick2(date);
            }, currentDate: date, date: moment(thisDate), startDate: startDate, endDate: endDate
        }));
    }

    const daysCount = days.length;
    for (i = 1; i <= 42 - daysCount; i++) {
        nextsMonth.date(i);
        days.push(React.createElement(Day, {
            key: moment(nextsMonth).format('DD MM YYYY'), onClick: function onClick(date) {
                return _onClick2(date);
            }, currentDate: date, date: moment(nextsMonth), startDate: startDate, endDate: endDate
        }));
    }

    return React.createElement(
        'nav',
        {className: 'calendar--days'},
        labels.concat(),
        days.concat()
    );
};

const Calendar = function (_React$Component) {
    _inherits(Calendar, _React$Component);

    function Calendar(props) {
        _classCallCheck(this, Calendar);

        const _this = _possibleConstructorReturn(this, _React$Component.call(this, props));

        _this.state = {
            date: moment(),
            startDate: moment().subtract(5, 'day'),
            endDate: moment().add(3, 'day')
        };
        return _this;
    }

    Calendar.prototype.resetDate = function resetDate() {
        this.setState({
            date: moment()
        });
    };

    Calendar.prototype.changeMonth = function changeMonth(month) {
        const date = this.state.date;

        date.month(month);

        this.setState(date);
    };

    Calendar.prototype.changeDate = function changeDate(date) {
        const _state = this.state;
        let startDate = _state.startDate;
        let endDate = _state.endDate;

        if (startDate === null || date.isBefore(startDate, 'day') || !startDate.isSame(endDate, 'day')) {
            startDate = moment(date);
            endDate = moment(date);
        } else if (date.isSame(startDate, 'day') && date.isSame(endDate, 'day')) {
            startDate = null;
            endDate = null;
        } else if (date.isAfter(startDate, 'day')) {
            endDate = moment(date);
        }

        this.setState({
            startDate: startDate,
            endDate: endDate
        });
    };

    Calendar.prototype.render = function render() {
        const _this2 = this;

        const _state2 = this.state;
        const date = _state2.date;
        const startDate = _state2.startDate;
        const endDate = _state2.endDate;

        return React.createElement(
            'div',
            {className: 'calendar'},
            React.createElement(Heading, {
                date: date, changeMonth: function changeMonth(month) {
                    return _this2.changeMonth(month);
                }, resetDate: function resetDate() {
                    return _this2.resetDate();
                }
            }),
            React.createElement(Days, {
                onClick: function onClick(date) {
                    return _this2.changeDate(date);
                }, date: date, startDate: startDate, endDate: endDate
            })
        );
    };

    return Calendar;
}(React.Component);

ReactDOM.render(React.createElement(Calendar, null), document.getElementById('calendar'));