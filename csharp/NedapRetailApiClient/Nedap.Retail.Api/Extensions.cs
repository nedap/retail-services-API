using System;
using System.Linq.Expressions;

namespace Nedap.Retail.Api
{
    internal static class Extensions
    {
        /// <summary>
        /// Requires non null parameter
        /// </summary>
        public static void Required<T>(this object parameter, Expression<Func<T>> expression)
        {
            if (parameter == null)
                throw new ArgumentNullException(GetParameterName(expression));
        }

        /// <summary>
        /// Requires string parameter to be have value
        /// </summary>
        public static void RequiredString<T>(this string parameter, Expression<Func<T>> expression)
        {
            if (string.IsNullOrEmpty(parameter))
                throw new ArgumentNullException(GetParameterName(expression));
        }

        private static string GetParameterName<T>(Expression<Func<T>> expression)
        {
            var body = ((MemberExpression)expression.Body);
            return body.Member.Name;
        }
    }
}